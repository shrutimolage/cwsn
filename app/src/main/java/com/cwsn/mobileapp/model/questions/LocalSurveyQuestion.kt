package com.cwsn.mobileapp.model.questions

import com.cwsn.mobileapp.local.table.MCQOptions

data class LocalSurveyQuestion(var serverQuestId:Int,
                               var question: String?,
                               var questionFormat: String?,
                               var questionType: String?,
                               var fieldRequired: Boolean,
                               var mcqOptionList:MutableList<MCQOptions>?)
