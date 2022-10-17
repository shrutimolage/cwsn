package com.cwsn.mobileapp.model.school

import com.google.gson.annotations.SerializedName

data class PendingSchoolResp(@SerializedName("status"  ) var status  : Boolean?        = null,
                             @SerializedName("data"    ) var data    : ArrayList<SchoolData>? = null,
                             @SerializedName("message" ) var message : String?         = null)
