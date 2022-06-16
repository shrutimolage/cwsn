package com.cwsn.mobileapp.di

import android.content.Context
import com.cwsn.mobileapp.BuildConfig
import com.cwsn.mobileapp.network.APIService
import com.cwsn.mobileapp.network.ApiHelper
import com.cwsn.mobileapp.network.ApiHelperImpl
import com.cwsn.mobileapp.network.NetworkConnectionInterceptor
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
Created by  on 14,June,2022
 **/
val networkModule = module {
    single { AppPreferences(androidContext()) }
    single { provideOkHttpClient(androidContext(),get()) }
    single{ provideRetrofit(get(),Utils.API_BASE_URL)}
    single { provideApiService(get()) }
}

private fun provideOkHttpClient(context: Context, appPref:AppPreferences) :OkHttpClient
{
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    }
    val okhttpBuilder:OkHttpClient.Builder=OkHttpClient.Builder()
        .connectTimeout(6000, TimeUnit.MILLISECONDS)
        .readTimeout(3, TimeUnit.MINUTES)
        .writeTimeout(3, TimeUnit.MINUTES)
        .addInterceptor(ChuckInterceptor(context).showNotification(true))
        .addInterceptor(NetworkConnectionInterceptor(context))
        .addInterceptor(loggingInterceptor)
        .addInterceptor(Interceptor{ chain->
            val request=chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Authorization","Bearer ${appPref.fetchAccessToken()}")
                .build()
            return@Interceptor chain.proceed(request)
        })
    return okhttpBuilder.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient,BASE_URL: String): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

private fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)
