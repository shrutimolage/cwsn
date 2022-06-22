package com.cwsn.mobileapp.di

import androidx.room.Room
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
Created by  on 22,June,2022
 **/
val databaseModule = module {

   single {
       Room.databaseBuilder(androidContext(),QuestionDatabase::class.java,"userdatabase")
           .build()
   }

}
