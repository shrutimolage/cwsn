package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.repository.IAllQuestRepository
import com.cwsn.mobileapp.repository.impl.AllQuestRepository
import com.cwsn.mobileapp.repository.impl.HomeRepository
import com.cwsn.mobileapp.repository.impl.LoginRepository
import com.cwsn.mobileapp.repository.impl.ProfileRepository
import com.cwsn.mobileapp.utils.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
Created by  on 16,June,2022
 **/

val repoModule = module {
    single<QuestionDao>{
        val database=get<QuestionDatabase>()
        database.questionDao()
    }
    single { AppPreferences(androidContext()) }
    single<IAllQuestRepository> {
        AllQuestRepository(get(),get())
    }
    single {
        LoginRepository(get(),get())
    }
    single {
        ProfileRepository(get())
    }
    single {
        HomeRepository(get())
    }
}