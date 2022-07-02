package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
Created by  on 01,July,2022
 **/
data class Questions(@Expose @SerializedName("success") var success:Boolean?=null,
                     @Expose @SerializedName("data") var data:List<SurveyQuestion>?=null,
                     @Expose @SerializedName("message") var message:String?=null)
