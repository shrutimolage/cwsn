package com.cwsn.mobileapp.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 30,June,2022
 **/
data class ClusterData(@Expose @SerializedName("name" ) var name : String? = null,
                       @Expose @SerializedName("id"   ) var id   : Int?    = null)
