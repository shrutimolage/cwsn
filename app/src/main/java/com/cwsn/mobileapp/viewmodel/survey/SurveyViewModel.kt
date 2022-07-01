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
    fun getAllQuestions() = liveData(Dispatchers.IO){
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
}