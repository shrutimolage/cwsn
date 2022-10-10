package com.cwsn.mobileapp.model.location

import com.google.gson.annotations.SerializedName

data class Viewport(@SerializedName("northeast" ) var northeast : Northeast? = null,
                    @SerializedName("southwest" ) var southwest : Southwest? = null)
