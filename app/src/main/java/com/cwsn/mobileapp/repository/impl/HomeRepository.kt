package com.cwsn.mobileapp.repository.impl

import android.content.Context
import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.location.LocationLatLng
import com.cwsn.mobileapp.model.school.PendingSchoolResp
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.ApiHelper
import com.cwsn.mobileapp.utils.Utils
import retrofit2.Response
import retrofit2.http.Url

/**
Created by  on 30,June,2022
 **/
class HomeRepository(private val apiHelper: ApiHelper)
{
    suspend fun getAllClusterDetails(): Response<Cluster> {
        return apiHelper.getAllClusterDetails()
    }

    suspend fun getAllDashboardCount(): Response<DashboardCount> {
        return apiHelper.getAllDashboardCount()
    }

    suspend fun getAllSchoolList(input:SchoolListInput): Response<SchoolList> {
        return apiHelper.getSchoolClusterWise(input)
    }

    suspend fun getGoogleLocLatLng(apiUrl: String):Response<LocationLatLng>
    {
        return apiHelper.getGoogleLocLatLng(apiUrl)
    }

    suspend fun getAllPendingSchool():Response<PendingSchoolResp>{
        return apiHelper.getAllPendingSchool()
    }

}