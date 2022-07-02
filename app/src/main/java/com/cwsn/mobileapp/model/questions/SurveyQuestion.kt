package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 01,July,2022
 **/
data class SurveyQuestion(@Expose @SerializedName("id") var id:Long?=null,
                          @Expose @SerializedName("question") var question:String?=null,
                          @Expose @SerializedName("type") var type: String?= null,
                          @Expose @SerializedName("options"     ) var options    : List<String>?= null,
                          @Expose @SerializedName("is_required" ) var isRequired : Int?= null,
                          @Expose @SerializedName("name") var name       : String?= null
)
