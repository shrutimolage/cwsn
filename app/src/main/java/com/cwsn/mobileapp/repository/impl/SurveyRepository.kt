package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.local.table.MCQOptions
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.model.questions.SurveyQuestList
import com.cwsn.mobileapp.model.survey.SurveyIn
import com.cwsn.mobileapp.model.survey.SurveyInput
import com.cwsn.mobileapp.model.survey.SurveyResponse
import com.cwsn.mobileapp.model.survey.surveyformat
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response
import retrofit2.http.Body

/**
Created by  on 01,July,2022
 **/
class SurveyRepository(private val apiHelper: ApiHelper,private val questDao: QuestionDao)
{

    suspend fun getAllLocalDBQuestion():List<AllQuestion>{
        return questDao.getAllQuestions()
    }

    suspend fun getAllMCQOptions(questId:Int):List<MCQOptions>{
        return questDao.getAllMCQOption(questId)
    }

    suspend fun getAllSurveyServerQuestion(input: QuestListInput): Response<SurveyQuestList>{
        return apiHelper.getAllSurveyServerQuestion(input)
    }

    suspend fun saveSurveyData(input:List<SurveyIn>):Response<SurveyResponse>{
        return apiHelper.saveSurveyData(input)
    }
}