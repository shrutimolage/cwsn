package com.cwsn.mobileapp.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.local.table.MCQOptions

/**
Created by  on 22,June,2022
 **/
@Dao
interface QuestionDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuestions(questions:List<AllQuestion>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMCQOptions(options:List<MCQOptions>)

    @Query("SELECT * FROM AllQuestion")
    suspend fun getAllQuestions():List<AllQuestion>

    @Query("SELECT * FROM MCQOptions WHERE questId = :questId")
    suspend fun getAllMCQOption(questId:Int):List<MCQOptions>

    @Query("DELETE FROM AllQuestion")
    suspend fun deleteSurveyQuestions()

    @Query("DELETE FROM MCQOptions")
    suspend fun deleteMCQOptions()
}