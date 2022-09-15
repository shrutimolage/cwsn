package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.utils.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule= module {
factory {
    AppPreferences(androidContext())
}
}