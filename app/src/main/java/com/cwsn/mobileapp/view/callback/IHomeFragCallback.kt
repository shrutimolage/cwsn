package com.cwsn.mobileapp.view.callback

/**
Created by  on 30,June,2022
 **/
interface IHomeFragCallback
{
    fun showProgress()
    fun hideProgress()
    fun gotoSchoolSurvey(schoolId: Int?, name: String?, address: String?) {
    }

    fun toggleAppTopBar() {

    }

    fun onNavigateOptionScreen(itemName: String) {

    }

    fun gotoLoginScreen() {

    }
}