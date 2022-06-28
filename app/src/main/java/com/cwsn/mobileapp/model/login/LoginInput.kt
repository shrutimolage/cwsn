package com.cwsn.mobileapp.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 28,June,2022
 **/
data class LoginInput(@Expose @SerializedName("email"    ) var email    : String? = null,
                     @Expose @SerializedName("password" ) var password : String? = null
)
