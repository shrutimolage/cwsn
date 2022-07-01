package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.profile.UserProfile
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import retrofit2.Response
import retrofit2.http.Body

/**
Created by  on 16,June,2022
 **/
interface ApiHelper
{
    suspend fun userLogin(emailId:String,password:String): Response<LoginModel>

    suspend fun getuserProfile():Response<UserProfile>

    suspend fun getAllClusterDetails():Response<Cluster>

    suspend fun getAllDashboardCount():Response<DashboardCount>

    suspend fun getSchoolClusterWise(input: SchoolListInput):Response<SchoolList>

    suspend fun getAllSurveyServerQuestion():Response<Questions>
}