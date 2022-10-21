package com.cwsn.mobileapp.model.task

import com.google.gson.annotations.SerializedName

data class TaskData(@SerializedName("id"                  ) var id                : Int?    = null,
                    @SerializedName("format_id"           ) var formatId          : Int?    = null,
                    var taskSelectedStatus          : Boolean    = false,
                    @SerializedName("format_name"         ) var formatName        : String? = null,
                    @SerializedName("type"                ) var type              : String? = null,
                    @SerializedName("district_id"         ) var districtId        : String? = null,
                    @SerializedName("district_name"       ) var districtName      : String? = null,
                    @SerializedName("block_id"            ) var blockId           : String? = null,
                    @SerializedName("block_name"          ) var blockName         : String? = null,
                    @SerializedName("cluster_id"          ) var clusterId         : String? = null,
                    @SerializedName("cluster_name"        ) var clusterName       : String? = null,
                    @SerializedName("school_id"           ) var schoolId          : String? = null,
                    @SerializedName("school_name"         ) var schoolName        : String? = null,
                    @SerializedName("schedule_date"       ) var scheduleDate      : String? = null,
                    @SerializedName("is_answer_submitted" ) var isAnswerSubmitted : Int?    = null,
                    @SerializedName("status"              ) var status            : Int?    = null,
                    @SerializedName("created_at"          ) var createdAt         : String? = null,
                    @SerializedName("created_by"          ) var createdBy         : Int?    = null,
                    @SerializedName("updated_at"          ) var updatedAt         : String? = null,
                    @SerializedName("updated_by"          ) var updatedBy         : String? = null,
                    @SerializedName("is_deleted"          ) var isDeleted         : Int?    = null,
                    @SerializedName("deleted_at"          ) var deletedAt         : String? = null,
                    @SerializedName("deleted_by"          ) var deletedBy         : String? = null,
                    @SerializedName("count_question"      ) var countQuestion     : Int?    = null)
