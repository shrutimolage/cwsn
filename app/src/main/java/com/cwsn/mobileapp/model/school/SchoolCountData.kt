package com.cwsn.mobileapp.model.school

import com.google.gson.annotations.SerializedName

data class SchoolCountData(@SerializedName("name") var itemName:String?=null,
                           @SerializedName("count") var itemCount:Int?=null,
                            var itemImage:Int?=null)
