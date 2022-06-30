package com.cwsn.mobileapp.di

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
    single { AppPreferences(androidContext()) }
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