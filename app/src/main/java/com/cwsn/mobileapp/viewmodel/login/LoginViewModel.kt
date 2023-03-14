package com.cwsn.mobileapp.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.home.ActivitiesInput
import com.cwsn.mobileapp.model.login.Data
import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.LoginRepository
import com.cwsn.mobileapp.utils.Utils
import kotlinx.coroutines.Dispatchers

/**
Created by  on 16,June,2022
 **/
class LoginViewModel(private val loginRepos:LoginRepository):ViewModel()
{
    fun performUserLogin(email:String,password:String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val loginResponse=loginRepos.appUserLogin(LoginInput(email,password))
            if(loginResponse.isSuccessful){
                loginResponse.body()?.data?.let {loginData->
                    if(loginData.appLogin==1){
                        loginData.districtId?.let {
                            loginRepos.savedUserSession(loginData.token!!,loginData.id!!,
                                loginData.name!!,loginData.blockId!!,
                                loginData.blockName!!, loginData.clusterId!!, it,loginData.district_name
                            )
                        }
                        emit(Resource.success(data = loginResponse, message = Utils.API_SUCCESS))
                    }
                    else{
                        emit(Resource.error(data = loginResponse, message = "Unauthorized user.Please contact admin"))
                    }
                }

            }
            else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            emit(Resource.error(data = null, message = ex.message?:"Error while API Call"))
        }
    }

    fun forgetPassword(email: String)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = loginRepos.performForgotPwd(Data(email))
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    if (response.isSuccessful) {

                        response.message()

                        emit(Resource.success(data = response, message = Utils.API_SUCCESS))
                    }
                    else {
                        emit(
                            Resource.error(
                                data = response,
                                message = "Unauthorized user.Please contact admin"
                            )
                        )
                    }
                }

            }
            else {
                emit(Resource.error(data = null, message = "Server Error"))
            }
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error while API Call"))
        }
    }



}