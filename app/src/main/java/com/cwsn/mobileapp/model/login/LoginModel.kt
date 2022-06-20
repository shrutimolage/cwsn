package com.cwsn.mobileapp.model.login

import android.service.autofill.UserData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 20,June,2022
 **/
data class LoginModel(@SerializedName("classesd"     ) var classesd    : ArrayList<String> = arrayListOf(),
                      @SerializedName("status"       ) var status      : Int?              = null,
                      @SerializedName("data"         ) var data        : LogInData?             = null,
                      @SerializedName("school"       ) var school      : UserSchool?           = null,
                      @SerializedName("_token"       ) var Token       : String?           = null,
                      @SerializedName("current_date" ) var currentDate : String?           = null,
                      @SerializedName("expire_on"    ) var expireOn    : String?           = null,
                      @SerializedName("msg"          ) var msg         : String?           = null)
