package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.network.APIService
import com.cwsn.mobileapp.network.ApiHelper
import com.cwsn.mobileapp.utils.LoggerUtils
import retrofit2.Response

/**
Created by  on 16,June,2022
 **/
class LoginRepository(private val apiHelper: APIService)
{
    suspend fun appUserLogin(email:String,password:String): Response<LoginModel> {
        LoggerUtils.error(LoggerUtils.APP_TAG,"email $email")
        return apiHelper.loginAPi(email,password)
    }
}