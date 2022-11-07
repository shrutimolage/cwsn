package com.cwsn.mobileapp.utils

import android.content.Context
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.model.ApiError
import com.cwsn.mobileapp.model.dashboard.DashboardItem
import com.cwsn.mobileapp.model.home.SlideModel
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.util.concurrent.Callable

/**
Created by  on 16,June,2022
 **/
object Utils
{
    val SCHOOLNAME: String="school_name"
    val FORMID: String="form_id"
    val SCHOOL_ADDRS: String="school address"
    val SCHOOL_NAME: String="school name"
    val SCHOOL_ID: String="school id"
    val APP_DB_NAME: String="question_database"
    val NO_SCHOOL_FOUND: String="No school found"
    val API_SUCCESS: String="Success"
    val NO_NETWORK_FOUND_ERROR_MESG="Make sure you have an active data connection"
    val API_BASE_URL="https://paatham.us/cwsn_application/"
    val GOOGLE_API_URL:String="https://maps.googleapis.com/maps/"
    val SESSION_EXPIRE: String="session_expire"

    fun generateSlidePanelItems(): MutableList<SlideModel> {
        val itemList:MutableList<SlideModel> = mutableListOf()

        itemList.add(SlideModel(false,"Home", R.drawable.home_icon_new))
        itemList.add(SlideModel(false,"Resource Room",R.drawable.ic_resource_room_slide))
        itemList.add(SlideModel(false,"Monitoring",R.drawable.ic_monitoring_slide))
        itemList.add(SlideModel(false,"Logout",R.drawable.ic_logout_blue_icon))
        return itemList
    }

    fun getHttpStatusDetails(code: Int, responseBody: ResponseBody):String{
        var result =" "
        when(code){
            400->{
                val errorJson = Gson().fromJson(responseBody.string(), ApiError::class.java)
                result="${errorJson.message}"

            }
            401->{
                val errorJson = Gson().fromJson(responseBody.string(), ApiError::class.java)
                result="${errorJson.message}"
            }
            500,501,502,503->{
                val errorJson = Gson().fromJson(responseBody.string(), ApiError::class.java)
                result="Server Error ${errorJson.message}"
            }
        }
        return result
    }

    fun generateDashboardItem():MutableList<DashboardItem>{
        val itemList:MutableList<DashboardItem> = mutableListOf()

        itemList.add(DashboardItem("School",R.drawable.ic_school_icon))
        itemList.add(DashboardItem("Resource Room",R.drawable.resource_room_icon))
        itemList.add(DashboardItem("Monitoring",R.drawable.monitoring_icon))
        itemList.add(DashboardItem("School Visited",R.drawable.ic_visited_school_icon))
        itemList.add(DashboardItem("School Pending",R.drawable.ic_pending_school_icon))
        return itemList
    }

    fun getDataFromJsonFile(context: Context, jsonFileName:Int):String?{
        try{
            val inputStream = context.resources.openRawResource(jsonFileName)
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            return String(bytes)
        }
        catch (ex:Exception){
            ex.printStackTrace()
            return null
        }
    }
}