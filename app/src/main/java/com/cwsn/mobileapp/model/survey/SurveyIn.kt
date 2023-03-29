package com.cwsn.mobileapp.model.survey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SurveyIn(
 @Expose @SerializedName("school_id") var school_id: Int? = null,
 @Expose @SerializedName("teacher_id") var teacherid: Int? = null,
 @Expose @SerializedName("question_type") var question_type: String? = null,
 @Expose @SerializedName("format_id") var formatid: Int? = null,
 @Expose @SerializedName("question_format") var questionformat: String? = null,
 @Expose @SerializedName("district_id") var districtid: Int? = null,
 @Expose @SerializedName("district_name") var districtname: String? = null,
 @Expose @SerializedName("block_id") var blockid: Int? = null,
 @Expose @SerializedName("block_name") var blockname: String? = null,
 @SerializedName        ("answers"       ) var answers           : MutableList<Answer> = arrayListOf())
 //val list: ArrayList<String>


   //val answers: List<Answer>

