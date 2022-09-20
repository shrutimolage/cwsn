package com.cwsn.mobileapp.model.resourceRoom

import com.google.gson.annotations.SerializedName

data class ResRoomDetails(@SerializedName("name") var itemName:String?=null,
@SerializedName("count") var itemCount:Int?=null)