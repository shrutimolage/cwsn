package com.cwsn.mobileapp.model.home

import com.google.gson.annotations.SerializedName

/**
Created by  on 30,June,2022
 **/
data class SchoolCount(
    @SerializedName("cwsn_enrollment"          ) var cwsn_enrollment         : Int? = null,
    @SerializedName("school_having_cwsn"  ) var schoolHavingCWSN  : Int? = null,
    @SerializedName("school_having_ramps" ) var schoolHavingRamps : Int? = null,
    @SerializedName("school_having_cwsn_toilet" ) var totalCwsnToilet : Int? = null
)
