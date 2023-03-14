package com.cwsn.mobileapp.model.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 29,June,2022
 **/
data class UserProfile(@Expose @SerializedName("user" ) var user : User? = null)
