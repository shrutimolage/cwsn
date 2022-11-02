package com.cwsn.mobileapp.model.survey

import com.google.gson.annotations.SerializedName

data class SurveyInput(@SerializedName("question_id"     ) var questionId     : Int?    = null,
                       @SerializedName("school_id"       ) var schoolId       : Int?    = null,
                       @SerializedName("teacher_id"      ) var teacherId      : Int?    = null,
                       @SerializedName("question_name"   ) var questionName   : String? = null,
                       @SerializedName("question_type"   ) var questionType   : String? = null,
                       @SerializedName("question_format" ) var questionFormat : String? = null,
                       @SerializedName("user_answer"     ) var userAnswer     : Int?    = null,
                       @SerializedName("form_id"         ) var formId         : Int?    = null,
                       @SerializedName("address"         ) var address        : String? = null)
