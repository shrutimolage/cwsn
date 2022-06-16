package com.cwsn.mobileapp.appl

import android.app.Application
import com.cwsn.mobileapp.di.appModule
import com.cwsn.mobileapp.di.networkModule
import com.cwsn.mobileapp.di.repoModule
import com.cwsn.mobileapp.di.viewModelModule
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
            modules(appModule, networkModule, repoModule, viewModelModule)
        }
    }
}