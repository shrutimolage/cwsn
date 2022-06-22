package com.cwsn.mobileapp.repository.impl

import android.content.Context
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.local.table.AllQuestion

/**
Created by  on 22,June,2022
 **/
class AllQuestRepository(context: Context) {
    private var questDao: QuestionDao? = null

    init {
        val dbInstance = QuestionDatabase.getInstance(context)
        questDao = dbInstance.questionDao()
    }

    suspend fun saveAllQuestion(questionList: List<AllQuestion>) {
        questDao?.insertAllQuestions(questionList)
    }

    suspend fun getAllLocalQuestionData(): List<AllQuestion> {
        return questDao?.getAllQuestions()!!
    }
}