package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.local.table.MCQOptions
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

/**
Created by  on 01,July,2022
 **/
class SurveyRepository(private val apiHelper: ApiHelper,private val questDao: QuestionDao)
{
    suspend fun getAllSurveyServerQuestion(): Response<Questions>
    {
        return apiHelper.getAllSurveyServerQuestion()
    }

    suspend fun getAllLocalDBQuestion():List<AllQuestion>{
        return questDao.getAllQuestions()
    }

    suspend fun getAllMCQOptions(questId:Int):List<MCQOptions>{
        return questDao.getAllMCQOption(questId)
    }
}