package com.cwsn.mobileapp.viewmodel.monitoring

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.home.ActivitiesInput
import com.cwsn.mobileapp.model.monitoring.BlockSchoolDetails
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.MonitoringRepository
import com.cwsn.mobileapp.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

class MonitorViewModel(private val monitorRepo: MonitoringRepository):ViewModel()
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
    fun activitiesList(id: Int?)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = id?.let { monitorRepo.activitieslist(it) }
            if (response != null) {
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        if (response.isSuccessful) {

                            response.message()

                            emit(Resource.success(data = response, message = Utils.API_SUCCESS))
                        } else {
                            emit(
                                Resource.error(
                                    data = response,
                                    message = "Unauthorized user.Please contact admin"
                                )
                            )
                        }
                    }

                } else {
                    emit(Resource.error(data = null, message = "Server Error"))
                }
            }
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error while API Call"))
        }
    }


}