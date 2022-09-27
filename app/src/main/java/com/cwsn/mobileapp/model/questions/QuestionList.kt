package com.cwsn.mobileapp.model.questions

import com.google.gson.annotations.SerializedName

data class QuestionList(@SerializedName("questionist" ) var questionist : ArrayList<Questionist> = arrayListOf())
