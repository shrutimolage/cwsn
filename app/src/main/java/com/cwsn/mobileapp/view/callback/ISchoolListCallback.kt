package com.cwsn.mobileapp.view.callback

/**
Created by  on 02,July,2022
 **/
interface ISchoolListCallback:IAppBaseCallback
{
    fun startSchoolSurvey(schoolId: Int?, name: String?, address: String?){}
}