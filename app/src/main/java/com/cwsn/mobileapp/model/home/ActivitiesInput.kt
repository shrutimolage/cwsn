package com.cwsn.mobileapp.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ActivitiesInput(@Expose @SerializedName("type_id"    ) var type_id    : Int? = null)
