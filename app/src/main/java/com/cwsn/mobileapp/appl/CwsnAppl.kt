package com.cwsn.mobileapp.appl

import android.app.Application
import com.cwsn.mobileapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
Created by  on 14,June,2022
 **/

class CwsnAppl : Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CwsnAppl)
            modules(databaseModule,networkModule, repoModule, viewModelModule)
        }
    }
}