package com.cwsn.mobileapp.utils

import android.content.Context
import android.content.SharedPreferences


/**
Created by  on 16,June,2022
 **/
class AppPreferences(private val context: Context)
{
    private val PREF_NAME = "CWSNPref"
    private var mInstance: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    val KEY_TOKEN = "token"

    init {
        mInstance=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        editor=mInstance?.edit()
    }

    fun setUserLoginData(access_token:String){
        editor?.putString(KEY_TOKEN,access_token)
        editor?.commit()
    }

    fun fetchAccessToken(): String? {
        return mInstance?.getString(KEY_TOKEN,"")
    }
}