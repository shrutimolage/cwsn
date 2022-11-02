package com.cwsn.mobileapp.model.survey

import com.google.gson.annotations.SerializedName

data class SurveyResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("data"    ) var data    : String?  = null,
    @SerializedName("message" ) var message : String?  = null)
