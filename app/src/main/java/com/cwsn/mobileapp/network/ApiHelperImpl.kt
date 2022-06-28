package com.cwsn.mobileapp.network

import androidx.lifecycle.LiveData
import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import retrofit2.Response

/**
Created by  on 16,June,2022
 **/
class ApiHelperImpl(private val apiService: APIService) : ApiHelper
{
    override suspend fun userLogin(emailId: String, password: String): Response<LoginModel> {
        return apiService.loginAPi(LoginInput(emailId,password))
    }

}