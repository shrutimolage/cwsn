package com.cwsn.mobileapp.viewmodel.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.model.survey.SurveyIn
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.SurveyRepository
import com.cwsn.mobileapp.utils.Utils
import kotlinx.coroutines.Dispatchers

/**
Created by  on 01,July,2022
 **/
class SurveyViewModel(private val repos:SurveyRepository):ViewModel()
{


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

    fun getAllQuestionByFormId(input: QuestListInput) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val response = repos.getAllSurveyServerQuestion(input)
            if(response.isSuccessful){
                response.body()?.data?.let {
                    if(it.size>0){
                        emit(Resource.success(data=response, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = "No Questions Found"))
                    }
                }
            }
            else{
                emit(Resource.error(data = null, message = Utils.getHttpStatusDetails(
                    response.code(),response.errorBody()!!)))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }

    fun saveSurveyData(input: SurveyIn) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val response = repos.saveSurveyData(input)
            if(response.isSuccessful){
                emit(Resource.success(data = response, message = "Success"))
            }
            else{
                emit(Resource.error(data = null, message = Utils

                    .getHttpStatusDetails(response.code(),response.errorBody()!!)))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "${ex.message}"))
        }
    }




}