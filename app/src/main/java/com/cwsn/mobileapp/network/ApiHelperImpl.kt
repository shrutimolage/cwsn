package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.profile.UserProfile
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import retrofit2.Response

/**
Created by  on 16,June,2022
 **/
class ApiHelperImpl(private val apiService: APIService) : ApiHelper
{
    override suspend fun userLogin(emailId: String, password: String): Response<LoginModel> {
        return apiService.loginAPi(LoginInput(emailId,password))
    }

    override suspend fun getuserProfile(): Response<UserProfile> {
        return apiService.getUserProfile()
    }

    override suspend fun getAllClusterDetails(): Response<Cluster> {
        return apiService.getAllCluster()
    }

    override suspend fun getAllDashboardCount(): Response<DashboardCount> {
        return apiService.getAllDashboardCount()
    }

    override suspend fun getSchoolClusterWise(input: SchoolListInput): Response<SchoolList> {
        return apiService.getSchoolClusterWise(input)
    }

    override suspend fun getAllSurveyServerQuestion(): Response<Questions> {
        return apiService.getAllSurveyServerQuestion()
    }

}