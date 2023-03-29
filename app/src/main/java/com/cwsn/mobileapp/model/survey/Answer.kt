package com.cwsn.mobileapp.model.survey

data class Answer( val form_id: Int?,
                   val format_id: Int?,
                   val question_format: String?,
                   val question_id: Int?,
                   val question_name: String?,
                   val question_type: String?,
                   val school_id: Int?,
                   val user_answer: String?
)