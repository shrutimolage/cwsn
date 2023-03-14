package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.home.ActivitiesType
import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.location.LocationLatLng
import com.cwsn.mobileapp.model.school.PendingSchoolResp
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.model.school.VisitedSchoolResp
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

/**
Created by  on 30,June,2022
 **/
class HomeRepository(private val apiHelper: ApiHelper)
{
    suspend fun getAllClusterDetails(id:Int): Response<Cluster> {
        return apiHelper.getAllClusterDetails(id)
    }
    suspend fun getFormTypeList(): Response<ActivitiesType> {
        return apiHelper.getFormTypeList()
    }

    suspend fun getAllSchoolDetailCount(): Response<DashboardCount> {
        return apiHelper.getAllSchoolDetailCount()
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

    suspend fun getAllVisitedSchool():Response<VisitedSchoolResp>{
        return apiHelper.getAllVisitedSchool()
    }

}