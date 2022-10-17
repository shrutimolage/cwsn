package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.task.AllTaskList
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

class TaskRepository(private val apiHelper: ApiHelper)
{
    suspend fun getAllTaskActivityList(): Response<AllTaskList>{
        return apiHelper.getAllTaskActivityList()
    }
}