package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

/**
Created by  on 01,July,2022
 **/
class SurveyRepository(private val apiHelper: ApiHelper)
{
    suspend fun getAllSurveyServerQuestion(): Response<Questions>
    {
        return apiHelper.getAllSurveyServerQuestion()
    }
}