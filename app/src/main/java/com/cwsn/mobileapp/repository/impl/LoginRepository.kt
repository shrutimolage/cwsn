package com.cwsn.mobileapp.repository.impl


import com.cwsn.mobileapp.model.login.Data
import com.cwsn.mobileapp.model.login.ForgotPassword
import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.network.APIService
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import retrofit2.Response

/**
Created by  on 16,June,2022
 **/
class LoginRepository(private val apiHelper: APIService,private val appPref:AppPreferences,private val apiHelp:APIService)
{
    suspend fun appUserLogin(input:LoginInput): Response<LoginModel> {
        LoggerUtils.error(LoggerUtils.APP_TAG,"email ${input.email}")
        return apiHelper.loginAPi(input)
    }

    fun savedUserSession(token:String,teacherId:Int,teacherName:String,blockId:Int,blockName:String,clusterId:String,district_id:String,district_name:String?){
        appPref.setUserLoginData(token,teacherName,teacherId,blockId,blockName,clusterId,district_id,district_name)
    }

    suspend fun performForgotPwd(input: Data): Response<ForgotPassword> {
        LoggerUtils.error(LoggerUtils.APP_TAG,"email ${input.email}")
        return apiHelp.forgotPassword(input)
    }


}