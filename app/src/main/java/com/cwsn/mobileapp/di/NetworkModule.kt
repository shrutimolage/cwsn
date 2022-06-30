package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.network.APIService
import com.cwsn.mobileapp.network.ApiHelper
import com.cwsn.mobileapp.network.ApiHelperImpl
import com.cwsn.mobileapp.network.NetworkConnectionInterceptor
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
Created by  on 14,June,2022
 **/
val networkModule = module {
    single {AppPreferences(androidContext())}
    single {HttpLoggingInterceptor().apply { this.level=HttpLoggingInterceptor.Level.BODY }}
    single { NetworkConnectionInterceptor(androidContext()) }
    single { OkHttpClient.Builder()
        .addInterceptor(get<HttpLoggingInterceptor>())
        .addInterceptor(get<NetworkConnectionInterceptor>())
        .addInterceptor(Interceptor{ chain->
            val request=chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Authorization",getRequiredAuthorization(get()))
                .build()
            return@Interceptor chain.proceed(request)
        })
        .build() }
    single { Retrofit.Builder().client(get())
        .baseUrl(Utils.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()}
    single { get<Retrofit>().create(APIService::class.java) }
    single<ApiHelper>{
        return@single ApiHelperImpl(get())
    }
}

fun getRequiredAuthorization(appPref: AppPreferences): String {
    var result =""
    appPref.getUserLoginStatus()?.let { status->
        if(status){
            result="Bearer ${appPref.fetchAccessToken()}"
        }
    }
    return result
}
