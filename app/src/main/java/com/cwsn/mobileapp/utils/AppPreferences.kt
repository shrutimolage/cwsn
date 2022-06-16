package com.cwsn.mobileapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


/**
Created by  on 16,June,2022
 **/
class AppPreferences(private val context: Context)
{
    private var mInstance: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    val KEY_TOKEN = "token"

    init {
        val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        mInstance=EncryptedSharedPreferences.create(
            context,"secret_shared_prefs",masterKeyAlias,EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
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