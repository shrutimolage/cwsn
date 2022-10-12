package com.cwsn.mobileapp.network

import android.content.Context
import android.content.Intent
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.view.activity.LoginSignUpAct
import okhttp3.Interceptor
import okhttp3.Response

class SessionExpireInterceptor(private val _context: Context,private val appPreferences: AppPreferences):
    Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if(response.code==401){
            appPreferences.performAppLogout()
            val expireIntent = Intent(_context, LoginSignUpAct::class.java)
            expireIntent.putExtra(Utils.SESSION_EXPIRE,true)
            expireIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            expireIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            expireIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            expireIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            _context.startActivity(expireIntent)
        }
        return response
    }

}