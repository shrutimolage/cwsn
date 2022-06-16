package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.repository.IHelloRepository
import com.cwsn.mobileapp.repository.impl.HelloRepository
import com.cwsn.mobileapp.viewmodel.hello.HelloViewModel
import org.koin.dsl.module

/**
Created by  on 14,June,2022
 **/

val appModule= module {
    single<IHelloRepository>{
        HelloRepository()
    }
    single<HelloViewModel>{
        HelloViewModel(get())
    }
}