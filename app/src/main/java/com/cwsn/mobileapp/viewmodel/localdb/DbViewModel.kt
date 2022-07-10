package com.cwsn.mobileapp.viewmodel.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.local.table.MCQOptions
import com.cwsn.mobileapp.model.questions.SurveyQuestion
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.IAllQuestRepository
import kotlinx.coroutines.Dispatchers

/**
Created by  on 22,June,2022
 **/
class DbViewModel(private val questRepository: IAllQuestRepository):ViewModel()
{
    fun getAllQuestions() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val allLocalQuestionData = questRepository.getAllLocalQuestionData()
            if(allLocalQuestionData.isNotEmpty()){
                emit(Resource.success(data = allLocalQuestionData,message="All Question"))
            }else{
                emit(Resource.error(data = null, message = "No question found"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "Error while getting questions"))
        }
    }

    fun performAppLogout() = liveData(Dispatchers.IO)
    {
        emit(Resource.loading(data = null))
        try{
            emit(Resource.success(data = questRepository.performAppLogout(), message = "Logout Done"))
        }
        catch (ex:Exception){
            emit(Resource.error(data = null, message = "Error while app logout"))
        }
    }

    fun getAllSurveyQuestions(schoolId: Int?) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            questRepository.deleteSurveyQuestions()
            questRepository.deleteMCQOptions()
            val allSurveyServerQuestion = questRepository.getAllSurveyServerQuestion()
            if(allSurveyServerQuestion.isSuccessful){
                allSurveyServerQuestion.body()?.data?.let { surveyQuest->
                    if(surveyQuest.isNotEmpty()){
                        val allQuestions=generateQuestions(surveyQuest,schoolId)
                        val allMCQOptions = generateMCQOptions(surveyQuest)
                        questRepository.saveAllQuestion(allQuestions)
                        questRepository.saveAllMCQOptions(allMCQOptions)
                        emit(Resource.success(data = allSurveyServerQuestion, message = "Success"))
                    }
                    else{
                        emit(Resource.error(data = null, message = "No survey question found"))
                    }
                }
            }else{
                emit(Resource.error(data = null, message = "Server Error ${allSurveyServerQuestion.code()}"))
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
            emit(Resource.error(data = null, message = "API Error while getting questions"))
        }
    }

    private fun generateMCQOptions(surveyQuest: List<SurveyQuestion>) :List<MCQOptions>{
        var mcqOptions:MutableList<MCQOptions> = mutableListOf()
        for(surveyQuest in surveyQuest){
            if(surveyQuest.type.equals("radio")){
                surveyQuest.options?.forEachIndexed { index, data ->
                    val mcqOption = MCQOptions(surveyQuest.id?.toInt()!!,0,data)
                    mcqOptions.add(mcqOption)
                }
            }
        }
        return mcqOptions
    }

    private fun generateQuestions(surveyQuest: List<SurveyQuestion>, schoolId: Int?): List<AllQuestion>
    {
        var surveyQuestions:MutableList<AllQuestion> = mutableListOf()
        for(surveyQuest in surveyQuest){
            var allQuestion=AllQuestion(schoolId!!,surveyQuest.id?.toInt()!!,surveyQuest.question,
            surveyQuest.name,surveyQuest.type,getRequiredStatus(surveyQuest.isRequired),"")

            surveyQuestions.add(allQuestion)
        }
        return surveyQuestions
    }

    private fun getRequiredStatus(required: Int?): Boolean {
        return required==1
    }
}