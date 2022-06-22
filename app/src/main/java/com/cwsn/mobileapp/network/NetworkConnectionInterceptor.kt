package com.cwsn.mobileapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import com.cwsn.mobileapp.utils.Utils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.GzipSource
import java.io.IOException
import java.nio.charset.Charset

class NetworkConnectionInterceptor(context: Context):Interceptor
{
    private val applicationContext=context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(checkNetworkConnection() != "")
        {
            throw NoConnectivityException()
        }
        else{
            return chain.proceed(chain.request())
        }
    }

    private fun responseBody(response: Response): String? {
        val responseBody = response.body ?: return null
        val contentLength = responseBody.contentLength()

        if (contentLength == 0L) {
            return null
        }

        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source.buffer
        val headers = response.headers

        if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
            var gzippedResponseBody: GzipSource? = null
            try {
                gzippedResponseBody = GzipSource(buffer.clone())
                buffer = okio.Buffer()
                buffer.writeAll(gzippedResponseBody)
            } finally {
                gzippedResponseBody?.close()
            }
        }

        val charset: Charset = responseBody.contentType()?.charset(UTF8) ?: UTF8
        return buffer.clone().readString(charset)
    }

    private companion object {
        val UTF8: Charset = Charset.forName("UTF-8")
    }

    class NoConnectivityException: IOException() {
        override val message: String
            get() = Utils.NO_NETWORK_FOUND_ERROR_MESG
    }
    private fun checkNetworkConnection():String {
        var message = ""
        //check airplane mode
        if (isAirplaneMode(applicationContext)) {
            message = "Make sure you have an active data connection"
        }
        if (!isInternetAvailable(applicationContext)) {
            message = "Make sure you have an active data connection"
        }
        if (isAirplaneMode(applicationContext) && !isInternetAvailable(applicationContext))//airplane mode on but not internet
        {
            message = "Make sure you have an active data connection"
        }
        if (isAirplaneMode(applicationContext) && isInternetAvailable(applicationContext))//airplane mode on and wifi also on
        {
            message = ""
        }
        return message

    }

        @Suppress("DEPRECATION")
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }

        @Suppress("DEPRECATION")
        fun isAirplaneMode(context: Context): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0
                ) != 0;
            } else {
                return Settings.Global.getInt(
                    context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0
                ) != 0;
            }
        }

}