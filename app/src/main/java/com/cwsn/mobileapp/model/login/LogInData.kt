package com.cwsn.mobileapp.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogInData(@Expose @SerializedName("token"            ) var token: String? = null,
                     @Expose @SerializedName("name"             ) var name: String? = null,
                     @Expose @SerializedName("app_login"        ) var appLogin: Int? = null,
                     @Expose @SerializedName("id"               ) var id: Int?    = null,
                     @Expose @SerializedName("district_name"    ) var district_name:String? =null,
                     @Expose @SerializedName("division"         ) var divisionId: Int?    = null,
                     @Expose @SerializedName("district"         ) var districtId: String? = null,
                     @Expose @SerializedName("block"            ) var blockId: Int?    = null,
                     @Expose @SerializedName("block_name"       ) var blockName: String?    = null,
                     @Expose @SerializedName("cluster"          ) var clusterId: String?   = null,
                     @Expose @SerializedName("created_at"       ) var createdAt: String? = null,
                     @Expose @SerializedName("token_expires_at" ) var tokenExpiresAt: String? = null)
