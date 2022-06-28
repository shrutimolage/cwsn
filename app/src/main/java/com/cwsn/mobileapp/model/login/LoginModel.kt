package com.cwsn.mobileapp.model.login

import android.service.autofill.UserData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 20,June,2022
 **/
data class LoginModel(@Expose @SerializedName("success" ) var success : Boolean? = null,
                      @Expose @SerializedName("data"    ) var data    : LogInData?    = null,
                      @Expose @SerializedName("message" ) var message : String?  = null
                        )
