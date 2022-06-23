package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.repository.impl.AllQuestRepository
import com.cwsn.mobileapp.repository.impl.LoginRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
Created by  on 16,June,2022
 **/

val repoModule = module {
    single {
        LoginRepository(get())
    }
}