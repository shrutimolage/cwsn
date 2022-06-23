package com.cwsn.mobileapp.repository.impl

import android.content.Context
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.repository.IAllQuestRepository

/**
Created by  on 22,June,2022
 **/
class AllQuestRepository(private val questDao: QuestionDao):IAllQuestRepository {

    override suspend fun saveAllQuestion(questions: List<AllQuestion>) {
        questDao.insertAllQuestions(questions)
    }

    override suspend fun getAllLocalQuestionData(): List<AllQuestion> {
      return questDao.getAllQuestions()
    }
}