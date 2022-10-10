package com.cwsn.mobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiError(@Expose @SerializedName("message")
                    val message:String?=null)
