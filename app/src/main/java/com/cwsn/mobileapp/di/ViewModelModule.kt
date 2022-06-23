package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import org.koin.dsl.module

/**
Created by  on 16,June,2022
 **/

val viewModelModule= module {
    single {
        LoginViewModel(get())
    }
}