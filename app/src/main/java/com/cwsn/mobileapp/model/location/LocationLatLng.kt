package com.cwsn.mobileapp.model.location

import com.google.gson.annotations.SerializedName

data class LocationLatLng(@SerializedName("plus_code" ) var plusCode : PlusCode?= PlusCode(),
                          @SerializedName("results"   ) var results  : ArrayList<LatLngResults> = arrayListOf(),
                          @SerializedName("status"    ) var status   : String?            = null)
