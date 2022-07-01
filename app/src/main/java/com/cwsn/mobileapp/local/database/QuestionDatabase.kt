package com.cwsn.mobileapp.local.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.local.table.MCQOptions
import com.cwsn.mobileapp.local.table.UserAnswer
import com.cwsn.mobileapp.utils.Utils

/**
Created by  on 22,June,2022
 **/
@Database(entities = [AllQuestion::class, MCQOptions::class,UserAnswer::class], version = 2, exportSchema = false)
abstract class QuestionDatabase:RoomDatabase()
{
    abstract fun questionDao():QuestionDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE:QuestionDatabase?=null
        fun getInstance(context: Context):QuestionDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance= Room.databaseBuilder(context,QuestionDatabase::class.java,Utils.APP_DB_NAME)
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}