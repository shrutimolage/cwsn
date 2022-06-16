package com.cwsn.mobileapp.viewmodel.hello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.IHelloRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

/**
Created by  on 14,June,2022
 **/
class HelloViewModel(private val repos:IHelloRepository):ViewModel()
{
    fun getApplicationMessage() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            emit(Resource.success(repos.giveHello()))
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = ex.message?:"Error while Call"))
        }
    }

}