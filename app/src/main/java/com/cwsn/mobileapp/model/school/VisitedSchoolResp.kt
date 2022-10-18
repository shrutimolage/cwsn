package com.cwsn.mobileapp.model.school

import com.google.gson.annotations.SerializedName

data class VisitedSchoolResp(@SerializedName("status"  ) var status  : Boolean?        = null,
                             @SerializedName("data"    ) var data    : ArrayList<VisitedSchool>? = null,
                             @SerializedName("message" ) var message : String?         = null)
