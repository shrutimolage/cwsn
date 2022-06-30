package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.profile.UserProfile
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import retrofit2.Response
import retrofit2.http.*

/**
Created by  on 16,June,2022
 **/
interface APIService
{
    @POST("api/login")
    suspend fun loginAPi(@Body input: LoginInput): Response<LoginModel>

    @GET("api/user-details")
    suspend fun getUserProfile():Response<UserProfile>

    @GET("api/cluster_list")
    suspend fun getAllCluster():Response<Cluster>

    @GET("api/total_school")
    suspend fun getAllDashboardCount():Response<DashboardCount>

    @POST("api/cluster_wise_school")
    suspend fun getSchoolClusterWise(@Body input:SchoolListInput):Response<SchoolList>

}