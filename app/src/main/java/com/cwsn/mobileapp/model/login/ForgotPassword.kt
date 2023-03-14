package com.cwsn.mobileapp.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgotPassword(
    @Expose @SerializedName("data" ) var data    : Data?    = null,
    val message: String,
    val status: Boolean
)