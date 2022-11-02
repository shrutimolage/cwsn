package com.cwsn.mobileapp.utils

import android.util.Log

/**
Created by  on 20,June,2022
 **/
object LoggerUtils
{
    var isLogEnabled = false

    const val SERVICE_LOG_TAG = "TestServiceLoG"
    const val APP_TAG = "TestProject"

    fun error(tag: String?, msg: String?) {
        if (isLogEnabled) Log.e(tag, msg!!)
    }

    fun info(tag: String?, msg: String?) {
        if (isLogEnabled) Log.i(tag, msg!!)
    }

    fun verbose(tag: String?, msg: String?) {
        if (isLogEnabled) Log.v(tag, msg!!)
    }
    fun debug(tag: String?, msg: String?) {
        if (isLogEnabled) Log.d(tag, msg!!)
    }
    fun printMessage(msg: String?) {
        if (isLogEnabled) println(msg)
    }
}