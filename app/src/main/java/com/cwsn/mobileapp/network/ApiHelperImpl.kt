package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.location.LocationLatLng
import com.cwsn.mobileapp.model.login.LoginInput
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.profile.UserProfile
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.model.questions.Questions
import com.cwsn.mobileapp.model.questions.SurveyQuestList
import com.cwsn.mobileapp.model.school.PendingSchoolResp
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.model.school.VisitedSchoolResp
import com.cwsn.mobileapp.model.survey.SurveyInput
import com.cwsn.mobileapp.model.survey.SurveyResponse
import com.cwsn.mobileapp.model.task.AllTaskList
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

    override suspend fun getAllSchoolDetailCount(): Response<DashboardCount> {
        return apiService.getAllSchoolDetailCount()
    }

    override suspend fun getSchoolClusterWise(input: SchoolListInput): Response<SchoolList> {
        return apiService.getSchoolClusterWise(input)
    }

    override suspend fun getAllSurveyServerQuestion(input: QuestListInput): Response<SurveyQuestList> {
        return apiService.getAllSurveyServerQuestion(input)
    }

    override suspend fun getGoogleLocLatLng(apiUrl: String): Response<LocationLatLng> {
        return apiService.getGoogleLocLatLng(apiUrl)
    }

    override suspend fun getAllTaskActivityList(): Response<AllTaskList> {
        return apiService.getAllTaskActivityList()
    }

    override suspend fun getAllPendingSchool(): Response<PendingSchoolResp> {
        return apiService.getAllPendingSchool()
    }

    override suspend fun getAllVisitedSchool(): Response<VisitedSchoolResp> {
        return apiService.getAllVisitedSchool()
    }

    override suspend fun saveSurveyData(input: List<SurveyInput>): Response<SurveyResponse> {
        return apiService.saveSurveyData(input)
    }

}