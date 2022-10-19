package com.cwsn.mobileapp.viewmodel.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.school.SchoolDetails
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.HomeRepository
import com.cwsn.mobileapp.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

/**
Created by  on 30,June,2022
 **/
class HomeViewModel(private val repos:HomeRepository):ViewModel()
{
    fun fetchAllCluster() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val allClusterDetails = repos.getAllClusterDetails()
            if(allClusterDetails.isSuccessful){
                allClusterDetails.body()?.data?.size?.let {
                    if(it>0)
                    {
                        emit(Resource.success(data = allClusterDetails, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = "No cluster found"))
                    }
                }
            }
            else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "API Error"))
        }
    }

    fun getAllSchoolDetailCount() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val allDashboardCount = repos.getAllSchoolDetailCount()
            if(allDashboardCount.isSuccessful){
                emit(Resource.success(data = allDashboardCount, message = "Success"))
            }else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "API Error"))
        }
    }

    fun getAllSchoolList(input:SchoolListInput) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val allSchoolList = repos.getAllSchoolList(input)
            if(allSchoolList.isSuccessful){
                allSchoolList.body()?.data?.let { schools->
                    if(schools.isNotEmpty()){
                        emit(Resource.success(data = allSchoolList, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = Utils.NO_SCHOOL_FOUND))
                    }
                }
            }else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "API Error"))
        }
    }

    fun getSchoolData(ctx: Context,fileName:Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val schoolDataResponse = Utils.getDataFromJsonFile(ctx,fileName)
            if(schoolDataResponse!=null&&schoolDataResponse.isNotEmpty()){
                val schoolDetails = Gson().fromJson(schoolDataResponse, SchoolDetails::class.java)
                emit(Resource.success(data=schoolDetails, message = "Success"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }

    fun getAllPendingSchool() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val response = repos.getAllPendingSchool()
            if(response.isSuccessful){
                response.body()?.data?.let {
                    if(it.size>0){
                        emit(Resource.success(data = response, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = "No School Found"))
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

    fun getAllVisitedSchool() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val response = repos.getAllVisitedSchool()
            if(response.isSuccessful){
                response.body()?.data?.let {
                    if(it.size>0){
                        emit(Resource.success(data=response, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = "No School Found"))
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