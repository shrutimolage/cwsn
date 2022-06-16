package com.cwsn.mobileapp.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cwsn.mobileapp.repository.IHelloRepository

/**
Created by  on 14,June,2022
 **/
class HelloRepository:IHelloRepository
{

    override suspend fun giveHello(): LiveData<String> {
        return MutableLiveData("Hello From CWSN Application")
    }

}