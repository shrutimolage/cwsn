package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

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
}