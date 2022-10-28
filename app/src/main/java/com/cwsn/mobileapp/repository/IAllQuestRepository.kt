package com.cwsn.mobileapp.repository

import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.local.table.MCQOptions
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.model.questions.SurveyQuestList
import retrofit2.Response

/**
Created by  on 23,June,2022
 **/
interface IAllQuestRepository
{
    suspend fun saveAllQuestion(questions:List<AllQuestion>)

    suspend fun saveAllMCQOptions(mcqOptions:List<MCQOptions>)

    suspend fun getAllLocalQuestionData():List<AllQuestion>

    suspend fun getAllLocalMCQOptions(questId:Int):List<MCQOptions>

    suspend fun deleteSurveyQuestions()

    suspend fun deleteMCQOptions()

    suspend fun performAppLogout():Boolean

    suspend fun getAllSurveyServerQuestion(): Response<SurveyQuestList>
}