package com.cwsn.mobileapp.model.task

import com.cwsn.mobileapp.model.home.Actlist_data
import com.google.gson.annotations.SerializedName

data class ActTak(  @SerializedName("data"    ) var data    : ArrayList<Actlist_data>? = null)
