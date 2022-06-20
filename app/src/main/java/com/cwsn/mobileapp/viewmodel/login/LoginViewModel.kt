package com.cwsn.mobileapp.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.LoginRepository
import kotlinx.coroutines.Dispatchers

/**
Created by  on 16,June,2022
 **/
class LoginViewModel(private val loginRepos:LoginRepository):ViewModel()
{
    fun performUserLogin(email:String,password:String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val loginResponse=loginRepos.appUserLogin(email,password)
            if(loginResponse.isSuccessful){
                emit(Resource.success(data = loginResponse))
            }
            else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            emit(Resource.error(data = null, message = ex.message?:"Error while API Call"))
        }
    }
}