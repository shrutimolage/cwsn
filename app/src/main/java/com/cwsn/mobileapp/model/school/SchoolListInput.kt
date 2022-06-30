package com.cwsn.mobileapp.model.school

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 30,June,2022
 **/
data class SchoolListInput(@Expose @SerializedName("clusterId" ) var clusterId : Int? = null)
