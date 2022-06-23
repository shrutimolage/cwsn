package com.cwsn.mobileapp.repository

import com.cwsn.mobileapp.local.table.AllQuestion

/**
Created by  on 23,June,2022
 **/
interface IAllQuestRepository
{
    suspend fun saveAllQuestion(questions:List<AllQuestion>)

    suspend fun getAllLocalQuestionData():List<AllQuestion>
}