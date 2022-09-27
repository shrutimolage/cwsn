package com.cwsn.mobileapp.model.task

import com.google.gson.annotations.SerializedName

data class Tasklist(@SerializedName("id"         ) var id        : Int?    = null,
                    @SerializedName("name"       ) var name      : String? = null,
                    @SerializedName("status"     ) var status    : Int?    = null,
                    @SerializedName("created_at" ) var createdAt : String? = null,
                    @SerializedName("created_by" ) var createdBy : Int?    = null,
                    @SerializedName("updated_by" ) var updatedBy : String? = null,
                    @SerializedName("updated_at" ) var updatedAt : String? = null,
                    @SerializedName("is_deleted" ) var isDeleted : Int?    = null,
                    @SerializedName("deleted_at" ) var deletedAt : String? = null,
                    @SerializedName("deleted_by" ) var deletedBy : String? = null)
