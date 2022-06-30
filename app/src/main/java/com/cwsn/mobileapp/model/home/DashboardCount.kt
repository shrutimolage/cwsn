package com.cwsn.mobileapp.model.home

import com.google.gson.annotations.SerializedName

/**
Created by  on 30,June,2022
 **/
data class DashboardCount(@SerializedName("status"  ) var status  : Boolean? = null,
                          @SerializedName("data"    ) var data    : SchoolCount?    = null,
                          @SerializedName("message" ) var message : String?  = null
)
