package com.cwsn.mobileapp.repository

import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.model.questions.Questions
import retrofit2.Response

/**
Created by  on 23,June,2022
 **/
interface IAllQuestRepository
{
    suspend fun saveAllQuestion(questions:List<AllQuestion>)

    suspend fun getAllLocalQuestionData():List<AllQuestion>

    suspend fun deleteSurveyQuestions()

    suspend fun performAppLogout():Boolean

    suspend fun getAllSurveyServerQuestion(): Response<Questions>
}