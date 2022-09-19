package com.cwsn.mobileapp.utils

import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.model.dashboard.DashboardItem
import com.cwsn.mobileapp.model.home.SlideModel

/**
Created by  on 16,June,2022
 **/
object Utils
{
    val SCHOOL_ADDRS: String="school address"
    val SCHOOL_NAME: String="school name"
    val SCHOOL_ID: String="school id"
    val APP_DB_NAME: String="question_database"
    val NO_SCHOOL_FOUND: String="No school found"
    val API_SUCCESS: String="Success"
    val NO_NETWORK_FOUND_ERROR_MESG="Make sure you have an active data connection"
    val API_BASE_URL="https://paatham.us/cwsn/"

    fun generateSlidePanelItems(): MutableList<SlideModel> {
        val itemList:MutableList<SlideModel> = mutableListOf()

        itemList.add(SlideModel(false,"Home", R.drawable.home_icon_new))
        itemList.add(SlideModel(false,"Resource Room",R.drawable.ic_resource_room_slide))
        itemList.add(SlideModel(false,"Monitoring",R.drawable.ic_monitoring_slide))
        return itemList
    }

    fun generateDashboardItem():MutableList<DashboardItem>{
        val itemList:MutableList<DashboardItem> = mutableListOf()

        itemList.add(DashboardItem("School",R.drawable.ic_school_dashboard))
        itemList.add(DashboardItem("Resource Room",R.drawable.ic_resource_room))
        itemList.add(DashboardItem("Monitoring",R.drawable.ic_monitoring_dashboard))
        return itemList
    }
}