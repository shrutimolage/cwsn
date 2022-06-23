package com.cwsn.mobileapp.viewmodel.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cwsn.mobileapp.repository.IAllQuestRepository

/**
Created by  on 23,June,2022
 **/
@Suppress("UNCHECKED_CAST")
class DbVMFactory(private val repos:IAllQuestRepository):ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DbViewModel(repos) as T
    }
}