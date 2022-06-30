package com.cwsn.mobileapp.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 30,June,2022
 **/
data class Cluster(@Expose @SerializedName("success" ) var success : Boolean?        = null,
                   @Expose @SerializedName("data"    ) var data    : List<ClusterData>? = null,
                   @Expose @SerializedName("message" ) var message : String?         = null
)
