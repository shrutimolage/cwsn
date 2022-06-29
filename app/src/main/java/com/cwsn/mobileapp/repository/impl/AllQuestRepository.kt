package com.cwsn.mobileapp.repository.impl

import android.content.Context
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.repository.IAllQuestRepository
import com.cwsn.mobileapp.utils.AppPreferences

/**
Created by  on 22,June,2022
 **/
class AllQuestRepository(private val questDao: QuestionDao,private val appPref:AppPreferences):IAllQuestRepository {

    override suspend fun saveAllQuestion(questions: List<AllQuestion>) {
        questDao.insertAllQuestions(questions)
    }

    override suspend fun getAllLocalQuestionData(): List<AllQuestion> {
      return questDao.getAllQuestions()
    }

    override suspend fun performAppLogout(): Boolean {
        appPref.performAppLogout()
        return true
    }


}