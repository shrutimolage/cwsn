package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.profile.UserProfile
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
Created by  on 16,June,2022
 **/
interface APIService
{
    @POST("api/login")
    suspend fun loginAPi(@Body input: LoginInput): Response<LoginModel>

    @GET("api/user-details")
    suspend fun getUserProfile():Response<UserProfile>
}