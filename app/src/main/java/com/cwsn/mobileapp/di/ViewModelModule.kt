package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import com.cwsn.mobileapp.viewmodel.profile.ProfileViewModel
import org.koin.dsl.module

/**
Created by  on 16,June,2022
 **/

val viewModelModule= module {
    single {
        LoginViewModel(get())
    }
    single {
        ProfileViewModel(get())
    }
}