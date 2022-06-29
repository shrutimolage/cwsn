package com.cwsn.mobileapp.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.ProfileRepository
import com.cwsn.mobileapp.utils.Utils
import kotlinx.coroutines.Dispatchers

/**
Created by  on 29,June,2022
 **/
class ProfileViewModel(private val repos:ProfileRepository):ViewModel()
{
    fun getUserProfile() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val fetchUserProfile = repos.fetchUserProfile()
            if(fetchUserProfile.isSuccessful){
                emit(Resource.success(data = fetchUserProfile, message = Utils.API_SUCCESS))
            }
            else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = ex.message?:"Error while API Call"))
        }
    }
}