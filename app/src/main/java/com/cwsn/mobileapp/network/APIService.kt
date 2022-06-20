package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.login.LoginModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
Created by  on 16,June,2022
 **/
interface APIService
{
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("login")
    suspend fun loginAPi(@Field("email") emailId: String?,@Field("password") password: String?): Response<LoginModel>
}