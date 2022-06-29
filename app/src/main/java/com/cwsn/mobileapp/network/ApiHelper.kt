package com.cwsn.mobileapp.network

import androidx.lifecycle.LiveData
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.profile.UserProfile
import retrofit2.Response

/**
Created by  on 16,June,2022
 **/
interface ApiHelper
{
    suspend fun userLogin(emailId:String,password:String): Response<LoginModel>

    suspend fun getuserProfile():Response<UserProfile>
}