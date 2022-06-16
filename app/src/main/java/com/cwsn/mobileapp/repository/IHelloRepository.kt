package com.cwsn.mobileapp.repository

import androidx.lifecycle.LiveData

/**
Created by  on 14,June,2022
 **/
interface IHelloRepository
{
    suspend fun giveHello(): LiveData<String>
}