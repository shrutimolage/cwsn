package com.cwsn.mobileapp.viewmodel.resourceroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.resourceRoom.ResourceRoomData
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

class ResRoomViewModel:ViewModel()
{
    fun getAllResourceRoomDetails(ctx: Context,fileName:Int) = liveData(Dispatchers.Default){
        emit(Resource.loading(data = null))
        try{
            val resourceRoomJson = Utils.getDataFromJsonFile(ctx, fileName)
            if(resourceRoomJson!=null&&resourceRoomJson.isNotEmpty()){
                val resourceRoomData = Gson().fromJson(resourceRoomJson, ResourceRoomData::class.java)
                emit(Resource.success(data = resourceRoomData, message = "Success"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }
}