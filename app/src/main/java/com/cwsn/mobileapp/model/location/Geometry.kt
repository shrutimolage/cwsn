package com.cwsn.mobileapp.model.location

import com.google.gson.annotations.SerializedName

data class Geometry(@SerializedName("location"      ) var location     : SearchLocation? = null,
                    @SerializedName("location_type" ) var locationType : String?   = null,
                    @SerializedName("viewport"      ) var viewport     : Viewport? = null)
