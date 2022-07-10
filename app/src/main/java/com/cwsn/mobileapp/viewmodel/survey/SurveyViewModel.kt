package com.cwsn.mobileapp.viewmodel.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.SurveyRepository
import kotlinx.coroutines.Dispatchers

/**
Created by  on 01,July,2022
 **/
class SurveyViewModel(private val repos:SurveyRepository):ViewModel()
{
    fun getAllServerQuestions() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val allSurveyServerQuestion = repos.getAllSurveyServerQuestion()
            if(allSurveyServerQuestion.isSuccessful){
                emit(Resource.success(data = allSurveyServerQuestion, message = "Success"))
            }
            else{
                emit(Resource.error(data = null, message = "Server Error"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "Error while API call ${ex.message}"))
        }
    }

    fun getAllLocalDBQuestions() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val allLocalDBQuestion = repos.getAllLocalDBQuestion()
            if(allLocalDBQuestion.isNotEmpty()){
                emit(Resource.success(data = allLocalDBQuestion, message = "Success"))
            }
            else{
                emit(Resource.error(data = null, message = "No local DB questions found"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "Error while DB fetch ${ex.message}"))
        }
    }

    fun getAllMCQOptions(questId:Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val allMCQOptions = repos.getAllMCQOptions(questId)
            if(allMCQOptions.isNotEmpty()){
                emit(Resource.success(data = allMCQOptions, message = "Success"))
            }
            else{
                emit(Resource.error(data = null, message = "No mcq option found"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "Error while DB fetch ${ex.message}"))
        }
    }
}