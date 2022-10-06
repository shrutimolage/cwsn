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
    val KEY_TEACHER_ID="teacher_id"
    val KEY_TEACHER_NAME="teacher_name"
    val IS_USER_LOGIN="user login status"
    val LOCATION_LATITUDE="location_latitude"
    val LOCATION_LONGITUDE="location_longitude"

    init {
        mInstance=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        editor=mInstance?.edit()
    }

    fun setUserLoginData(access_token:String,teacherName:String,teacherId:Int){
        editor?.putString(KEY_TOKEN,access_token)
        editor?.putInt(KEY_TEACHER_ID,teacherId)
        editor?.putString(KEY_TEACHER_NAME,teacherName)
        editor?.putBoolean(IS_USER_LOGIN,true)
        editor?.commit()
    }

    fun getUserLoginData():Map<String,String>{
        val userData=HashMap<String,String>()
        userData[KEY_TOKEN] = mInstance?.getString(KEY_TOKEN,"")!!
        userData[KEY_TEACHER_NAME]=mInstance?.getString(KEY_TEACHER_NAME,"")!!
        userData[KEY_TEACHER_ID]=mInstance?.getInt(KEY_TEACHER_ID,0).toString()
        return userData
    }

    fun getUserLoginStatus():Boolean?
    {
        return mInstance?.getBoolean(IS_USER_LOGIN,false)
    }
    fun fetchAccessToken(): String? {
        return mInstance?.getString(KEY_TOKEN,"")
    }

    fun updateLocationDetails(latitude:Double,longitude:Double){
        editor?.putString(LOCATION_LATITUDE, latitude.toString())
        editor?.putString(LOCATION_LONGITUDE,longitude.toString())
        editor?.commit()
    }

    fun performAppLogout(){
        editor?.clear()
        editor?.commit()
    }
}