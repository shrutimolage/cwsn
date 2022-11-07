package com.cwsn.mobileapp.view.callback

import com.cwsn.mobileapp.model.questions.LocalSurveyQuestion
import com.cwsn.mobileapp.model.questions.QuestionData

interface IQuestListInterface
{
    fun updateMCQOptionForId(quest: LocalSurveyQuestion){}
    fun refreshListAtPos(position: Int) {

    }

    fun refreshList() {

    }

    fun updateRadioOptionAnswer(questData: QuestionData,value:String){

    }
}