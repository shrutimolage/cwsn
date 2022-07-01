package com.cwsn.mobileapp.di

import androidx.room.Room
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.utils.Utils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
Created by  on 01,July,2022
 **/

val databaseModule = module{
    single {
        Room.databaseBuilder(androidContext(),QuestionDatabase::class.java,Utils.APP_DB_NAME).fallbackToDestructiveMigration().build()
    }
    single<QuestionDao>{
        val database=get<QuestionDatabase>()
        database.questionDao()
    }
}