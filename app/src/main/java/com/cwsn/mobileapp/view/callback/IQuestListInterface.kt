package com.cwsn.mobileapp.view.callback

import com.cwsn.mobileapp.model.questions.LocalSurveyQuestion

interface IQuestListInterface
{
    fun updateMCQOptionForId(quest: LocalSurveyQuestion)
}