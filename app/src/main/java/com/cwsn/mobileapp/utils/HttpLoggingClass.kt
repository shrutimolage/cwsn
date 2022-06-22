package com.cwsn.mobileapp.utils

import com.cwsn.mobileapp.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

/**
Created by  on 21,June,2022
 **/
class HttpLoggingClass
{
    fun provideHttpLogging():HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        return loggingInterceptor
    }
}