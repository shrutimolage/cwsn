package com.cwsn.mobileapp.di

import com.cwsn.mobileapp.repository.impl.IChangePwdRepos
import com.cwsn.mobileapp.viewmodel.ChangePwd.ChangePwdVM
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.cwsn.mobileapp.viewmodel.profile.ProfileViewModel
import com.cwsn.mobileapp.viewmodel.resourceroom.ResRoomViewModel
import com.cwsn.mobileapp.viewmodel.shared.SharedViewModel
import com.cwsn.mobileapp.viewmodel.survey.SurveyViewModel
import com.cwsn.mobileapp.viewmodel.task.TaskViewModel
import org.koin.dsl.module

/**
Created by  on 16,June,2022
 **/

val viewModelModule= module {
    single{
        DbViewModel(get())
    }
    single {
        LoginViewModel(get())
    }
    single {
        ProfileViewModel(get())
    }
    single{
        HomeViewModel(get())
    }
    single{
        SurveyViewModel(get())
    }
    single {
        ResRoomViewModel()
    }
    single{
        MonitorViewModel(get())
    }
    single{
        TaskViewModel(get())
    }
    single {
        SharedViewModel(get())
    }
    single {
      ChangePwdVM(get())
    }
}