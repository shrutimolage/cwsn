package com.cwsn.mobileapp.model.home

import com.google.gson.annotations.SerializedName

/**
Created by  on 30,June,2022
 **/
data class SchoolCount(
    @SerializedName("total_school"          ) var totalSchool         : Int? = null,
    @SerializedName("total_pending_school"  ) var totalPendingSchool  : Int? = null,
    @SerializedName("total_verified_school" ) var totalVerifiedSchool : Int? = null)
