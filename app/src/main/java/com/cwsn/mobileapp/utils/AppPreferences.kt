package com.cwsn.mobileapp.utils

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.KoinApplication.Companion.init


/**
Created by  on 16,June,2022
 **/
class AppPreferences(private val context: Context)
{
    private val PREF_NAME = "CWSNPref"
    private var mInstance: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    val KEY_TOKEN = "token"
    val IS_USER_LOGIN="user login status"

    init {
        mInstance=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        editor=mInstance?.edit()
    }

    fun setUserLoginData(access_token:String){
        editor?.putString(KEY_TOKEN,access_token)
        editor?.putBoolean(IS_USER_LOGIN,true)
        editor?.commit()
    }

    fun getUserLoginStatus():Boolean?
    {
        return mInstance?.getBoolean(IS_USER_LOGIN,false)
    }
    fun fetchAccessToken(): String? {
        return mInstance?.getString(KEY_TOKEN,"")
    }

    fun performAppLogout(){
        editor?.clear()
        editor?.commit()
    }
}