package com.cwsn.mobileapp.model.monitoring

import com.google.gson.annotations.SerializedName

data class SchoolList(@SerializedName("name"    ) var name    : String? = null,
                      @SerializedName("address") var address:String?=null,
                      @SerializedName("student" ) var student : Int?    = null)
