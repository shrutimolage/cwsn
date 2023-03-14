package com.cwsn.mobileapp.model.survey

import com.google.gson.annotations.SerializedName

data class SurveyInput(@SerializedName("question_id"     ) var questionId     : Int?    = null,
                       @SerializedName("school_id"       ) var schoolId       : Int?    = null,
                       @SerializedName("teacher_id"      ) var teacherId      : Int?    = null,
                       @SerializedName("question_name"   ) var questionName   : String? = null,
                       @SerializedName("question_type"   ) var  questionFormat  : String? = null,
                       @SerializedName("question_format" ) var questionType: String? = null,
                       @SerializedName("user_answer"     ) var userAnswer     : String?   = null,
                       @SerializedName("address"         ) var address        : String? = null,
                       @SerializedName("district_id"     )var district_id     :Int?=null,
                       @SerializedName("district_name"   )var district_name   : String? = null,
                       @SerializedName("block_id"        )var block_id        :Int?=null,
                       @SerializedName("block_name"      )var block_name      : String? = null,
                        @SerializedName("form_id"        )var formId         :Int?=null)