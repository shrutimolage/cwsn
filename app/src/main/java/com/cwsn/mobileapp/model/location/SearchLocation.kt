package com.cwsn.mobileapp.model.location

import com.google.gson.annotations.SerializedName

data class SearchLocation(@SerializedName("lat" ) var lat : Double? = null,
                          @SerializedName("lng" ) var lng : Double? = null)
