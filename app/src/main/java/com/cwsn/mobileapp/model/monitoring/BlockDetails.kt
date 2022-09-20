package com.cwsn.mobileapp.model.monitoring

import com.google.gson.annotations.SerializedName

data class BlockDetails(@SerializedName("id"   ) var id   : Int?    = null,
                        @SerializedName("name" ) var name : String? = null)
