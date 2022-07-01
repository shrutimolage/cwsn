package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 01,July,2022
 **/
data class SurveyQuestion(@Expose @SerializedName("id") var id:Long?=null,
                          @Expose @SerializedName("question") var question:String?=null,
)
