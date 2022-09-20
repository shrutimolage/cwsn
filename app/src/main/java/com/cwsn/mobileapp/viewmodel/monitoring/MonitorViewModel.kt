package com.cwsn.mobileapp.viewmodel.monitoring

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.monitoring.BlockSchoolDetails
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

class MonitorViewModel:ViewModel()
{
    fun getBlockWiseSchoolDetails(ctx: Context, fileName:Int) = liveData(Dispatchers.Default){
        emit(Resource.loading(data = null))
        try{
            val dataFromJsonFile = Utils.getDataFromJsonFile(ctx, fileName)
            if(dataFromJsonFile!=null&&dataFromJsonFile.isNotEmpty()){
                val response = Gson().fromJson(dataFromJsonFile, BlockSchoolDetails::class.java)
                emit(Resource.success(data = response, message = "Success"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }
}