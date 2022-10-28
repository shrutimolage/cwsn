package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.SerializedName

data class SurveyQuestList(@SerializedName("status"  ) var status  : Boolean?        = null,
                           @SerializedName("data"    ) var data    : ArrayList<QuestionData> = arrayListOf(),
                           @SerializedName("message" ) var message : String?         = null)
