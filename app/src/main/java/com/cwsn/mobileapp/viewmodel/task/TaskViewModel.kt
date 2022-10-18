package com.cwsn.mobileapp.viewmodel.task

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.task.AllTaskList
import com.cwsn.mobileapp.model.task.TaskListResponse
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.TaskRepository
import com.cwsn.mobileapp.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class TaskViewModel(private val repos:TaskRepository) : ViewModel()
{
    fun getAllTaskActivityList(ctx: Context, fileName:Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val response = Utils.getDataFromJsonFile(ctx, fileName)
            if(response!=null&&response.isNotEmpty()){
                val taskListData = Gson().fromJson(response, TaskListResponse::class.java)
                if(taskListData?.tasklist?.size!!>0)
                {
                    emit(Resource.success(data = taskListData, message = "Success"))
                }
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "Error on task list"))
        }
    }

    fun getAllTaskActList() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val response = repos.getAllTaskActivityList()
            if(response.isSuccessful){
                response.body()?.data?.let {
                    if(it.size>0){
                        emit(Resource.success(data = response, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = "No Task/Activity Found"))
                    }
                }
            }
            else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }
}