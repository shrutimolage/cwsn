package com.cwsn.mobileapp.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChangeInput(@Expose @SerializedName("token"    ) var email    : String? = null,
                      @Expose @SerializedName("password" ) var password : String? = null,
                       @Expose @SerializedName("password_confirm" ) var password_confirm : String? = null)