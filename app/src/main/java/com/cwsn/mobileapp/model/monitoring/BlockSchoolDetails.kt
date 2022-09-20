package com.cwsn.mobileapp.model.monitoring

import com.google.gson.annotations.SerializedName

data class BlockSchoolDetails(@SerializedName("blockDetails" ) var blockDetails : BlockDetails?         = BlockDetails(),
                              @SerializedName("schoolList"   ) var schoolList   : ArrayList<SchoolList> = arrayListOf()
)
