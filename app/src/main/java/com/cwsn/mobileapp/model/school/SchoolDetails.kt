package com.cwsn.mobileapp.model.school

import com.google.gson.annotations.SerializedName

data class SchoolDetails(@SerializedName("list") var details:ArrayList<SchoolCountData>?=null)
