package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.SerializedName

data class QuestionData(@SerializedName("id"                    ) var id                 : Int?              = null,
                        @SerializedName("form_id"               ) var formId             : Int?              = null,
                        @SerializedName("format_id"             ) var formatId           : Int?              = null,
                        @SerializedName("format_name"           ) var formatName         : String?           = null,
                        @SerializedName("question"              ) var question           : String?           = null,
                        @SerializedName("type"                  ) var type               : String?           = null,
                        @SerializedName("is_required"           ) var isRequired         : Int?              = null,
                        @SerializedName("option_type"           ) var optionType         : String?           = null,
                        @SerializedName("district_id"           ) var districtId         : String?           = null,
                        @SerializedName("district_name"         ) var districtName       : String?           = null,
                        @SerializedName("block_id"              ) var blockId            : String?           = null,
                        @SerializedName("block_name"            ) var blockName          : String?           = null,
                        @SerializedName("cluster_id"            ) var clusterId          : String?           = null,
                        @SerializedName("cluster_name"          ) var clusterName        : String?           = null,
                        @SerializedName("school_id"             ) var schoolId           : String?           = null,
                        @SerializedName("school_name"           ) var schoolName         : String?           = null,
                        @SerializedName("options"               ) var options            : ArrayList<String> = arrayListOf(),
                        @SerializedName("option_from"           ) var optionFrom         : String?           = null,
                        @SerializedName("accept_type"           ) var acceptType         : String?           = null,
                        @SerializedName("is_option_from_tables" ) var isOptionFromTables : Int?              = null,
                        @SerializedName("answer"                ) var answer             : String?           = null,
                        @SerializedName("status"                ) var status             : Int?              = null,
                        @SerializedName("created_at"            ) var createdAt          : String?           = null,
                        @SerializedName("created_by"            ) var createdBy          : Int?              = null,
                        @SerializedName("updated_at"            ) var updatedAt          : String?           = null,
                        @SerializedName("updated_by"            ) var updatedBy          : String?           = null,
                        @SerializedName("is_deleted"            ) var isDeleted          : Int?              = null,
                        @SerializedName("deleted_at"            ) var deletedAt          : String?           = null,
                        @SerializedName("deleted_by"            ) var deletedBy          : String?           = null,
var isEditing:Boolean=false,
var userTextAnswer:String="")