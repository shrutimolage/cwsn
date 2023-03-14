package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.home.Actist_Data
import com.cwsn.mobileapp.model.home.ActivitiesInput
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

class MonitoringRepository (private val apiHelper: ApiHelper){

    suspend fun activitieslist(id:Int): Response<Actist_Data> {
        return  apiHelper.getactivitiesList(id)
    }
}