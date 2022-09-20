package com.cwsn.mobileapp.model.resourceRoom

import com.google.gson.annotations.SerializedName

data class ResourceRoomData(@SerializedName("list") var resRoomList:ArrayList<ResRoomDetails>?=null)
