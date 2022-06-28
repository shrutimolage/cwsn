package com.cwsn.mobileapp.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogInData(@Expose @SerializedName("token"            ) var token          : String? = null,
                     @Expose @SerializedName("name"             ) var name           : String? = null,
                     @Expose @SerializedName("id"               ) var id             : Int?    = null,
                     @Expose @SerializedName("created_at"       ) var createdAt      : String? = null,
                     @Expose @SerializedName("token_expires_at" ) var tokenExpiresAt : String? = null)
