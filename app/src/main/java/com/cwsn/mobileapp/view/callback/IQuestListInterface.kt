package com.cwsn.mobileapp.view.callback

import com.cwsn.mobileapp.model.questions.LocalSurveyQuestion

interface IQuestListInterface
{
    fun updateMCQOptionForId(quest: LocalSurveyQuestion){}
    fun refreshListAtPos(position: Int) {

    }

    fun updateUserTextAnswer(userTextAnswer: String, position: Int) {

    }

    fun refreshList() {

    }
}