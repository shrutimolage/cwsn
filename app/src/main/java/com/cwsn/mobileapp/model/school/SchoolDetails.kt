package com.cwsn.mobileapp.model.school

import android.widget.ArrayAdapter
import com.google.gson.annotations.SerializedName

data class SchoolDetails(@SerializedName("list") var details:ArrayList<SchoolCountData>?=null)
