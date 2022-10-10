package com.cwsn.mobileapp.viewmodel.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.HomeRepository
import com.cwsn.mobileapp.utils.Utils
import kotlinx.coroutines.Dispatchers

class SharedViewModel(private val repos:HomeRepository) : ViewModel(){

    fun fetchLocationAddress(apiUrl:String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val response = repos.getGoogleLocLatLng(apiUrl)
            if(response.isSuccessful){
                emit(Resource.success(data = response, message = "Success"))
            }
            else{
                emit(Resource.error(data = null, message = Utils.getHttpStatusDetails(
                    response.code(),response.errorBody()!!)))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }

}