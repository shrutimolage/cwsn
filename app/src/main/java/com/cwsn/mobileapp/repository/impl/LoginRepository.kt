package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.network.APIService
import com.cwsn.mobileapp.network.ApiHelper
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import retrofit2.Response

/**
Created by  on 16,June,2022
 **/
class LoginRepository(private val apiHelper: APIService,private val appPref:AppPreferences)
{
    suspend fun appUserLogin(input:LoginInput): Response<LoginModel> {
        LoggerUtils.error(LoggerUtils.APP_TAG,"email ${input.email}")
        return apiHelper.loginAPi(input)
    }

    fun savedUserSession(token:String,teacherId:Int,teacherName:String){
        appPref.setUserLoginData(token,teacherName,teacherId)
    }
}