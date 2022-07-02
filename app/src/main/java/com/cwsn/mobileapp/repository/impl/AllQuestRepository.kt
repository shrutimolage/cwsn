package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.network.ApiHelper
import com.cwsn.mobileapp.repository.IAllQuestRepository
import com.cwsn.mobileapp.utils.AppPreferences
import retrofit2.Response

/**
Created by  on 22,June,2022
 **/
class AllQuestRepository(private val questDao: QuestionDao,private val appPref:AppPreferences,private val apiHelper: ApiHelper):IAllQuestRepository {

    override suspend fun saveAllQuestion(questions: List<AllQuestion>) {
        questDao.insertAllQuestions(questions)
    }

    override suspend fun getAllLocalQuestionData(): List<AllQuestion> {
      return questDao.getAllQuestions()
    }

    override suspend fun deleteSurveyQuestions() {
        questDao.deleteSurveyQuestions()
    }

    override suspend fun performAppLogout(): Boolean {
        appPref.performAppLogout()
        return true
    }

    override suspend fun getAllSurveyServerQuestion(): Response<Questions> {
        return apiHelper.getAllSurveyServerQuestion()
    }


}