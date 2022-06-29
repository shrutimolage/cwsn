package com.cwsn.mobileapp.model.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 29,June,2022
 **/
data class UserData(@Expose @SerializedName("id"                 ) var id               : Int?    = null,
                    @Expose @SerializedName("name"               ) var name             : String? = null,
                    @Expose @SerializedName("username"           ) var username         : String? = null,
                    @Expose @SerializedName("school_id"          ) var schoolId         : String? = null,
                    @Expose @SerializedName("email"              ) var email            : String? = null,
                    @Expose @SerializedName("email_verified_at"  ) var emailVerifiedAt  : String? = null,
                    @Expose @SerializedName("current_team_id"    ) var currentTeamId    : String? = null,
                    @Expose @SerializedName("profile_photo_path" ) var profilePhotoPath : String? = null,
                    @Expose @SerializedName("user_role"          ) var userRole         : String? = null,
                    @Expose @SerializedName("status"             ) var status           : String? = null,
                    @Expose @SerializedName("created_by"         ) var createdBy        : String? = null,
                    @Expose @SerializedName("created_ip"         ) var createdIp        : String? = null,
                    @Expose @SerializedName("updated_by"         ) var updatedBy        : String? = null,
                    @Expose @SerializedName("updated_ip"         ) var updatedIp        : String? = null,
                    @Expose @SerializedName("created_at"         ) var createdAt        : String? = null,
                    @Expose @SerializedName("updated_at"         ) var updatedAt        : String? = null,
                    @Expose @SerializedName("profile_photo_url"  ) var profilePhotoUrl  : String? = null)
