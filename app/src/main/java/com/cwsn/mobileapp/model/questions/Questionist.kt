package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.SerializedName

data class Questionist(@SerializedName("id"                    ) var id                 : Int?    = null,
                       @SerializedName("question"              ) var question           : String? = null,
                       @SerializedName("type"                  ) var type               : String? = null,
                       @SerializedName("task"                  ) var task               : String? = null,
                       @SerializedName("sub_task"              ) var subTask            : String? = null,
                       @SerializedName("is_required"           ) var isRequired         : Int?    = null,
                       @SerializedName("option_type"           ) var optionType         : String? = null,
                       @SerializedName("options"               ) var options            : String? = null,
                       @SerializedName("option_from"           ) var optionFrom         : String? = null,
                       @SerializedName("accept_type"           ) var acceptType         : String? = null,
                       @SerializedName("is_option_from_tables" ) var isOptionFromTables : Int?    = null,
                       @SerializedName("status"                ) var status             : Int?    = null,
                       @SerializedName("created_at"            ) var createdAt          : String? = null,
                       @SerializedName("created_by"            ) var createdBy          : Int?    = null,
                       @SerializedName("updated_at"            ) var updatedAt          : String? = null,
                       @SerializedName("updated_by"            ) var updatedBy          : String? = null,
                       @SerializedName("is_deleted"            ) var isDeleted          : Int?    = null,
                       @SerializedName("deleted_at"            ) var deletedAt          : String? = null,
                       @SerializedName("deleted_by"            ) var deletedBy          : String? = null
)
