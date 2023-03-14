package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.login.ChangeInput
import com.cwsn.mobileapp.model.login.ResetPassword
import com.cwsn.mobileapp.model.profile.ChangePwdInput
import com.cwsn.mobileapp.network.APIService
import retrofit2.Response

class IChangePwdRepos(private val apiHelper: APIService) {
 suspend  fun  resetPassword(data:ChangePwdInput): Response<ResetPassword> {
        return apiHelper.changeUserPassword(data)
    }

//    suspend fun appUserLogin(input: LoginInput): Response<LoginModel> {
//        LoggerUtils.error(LoggerUtils.APP_TAG,"email ${input.email}")
//        return apiHelper.loginAPi(input)
//    }
}