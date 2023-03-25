package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.*
import com.cwsn.mobileapp.model.location.LocationLatLng
import com.cwsn.mobileapp.model.login.*
import com.cwsn.mobileapp.model.profile.ChangePwdInput
import com.cwsn.mobileapp.model.profile.UserProfile
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.model.questions.SurveyQuestList
import com.cwsn.mobileapp.model.school.PendingSchoolResp
import com.cwsn.mobileapp.model.school.SchoolList
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.model.school.VisitedSchoolResp
import com.cwsn.mobileapp.model.survey.SurveyIn
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

 override suspend fun performForgotPwd(emailId: String): Response<ForgotPassword> {
        return apiService.forgotPassword(Data(emailId))
    }

override  suspend fun changeUserPassword(token: String,password:String,confom_password:String): Response<ResetPassword> {
        return apiService.changeUserPassword(ChangePwdInput(token,password,confom_password))
    }

    override suspend fun getuserProfile(): Response<UserProfile> {
        return apiService.getUserProfile()
    }

    override suspend fun getactivitiesList(id: Int): Response<Actist_Data> {
        return apiService.getactivitiesList(ActivitiesInput(id))
    }

    override suspend fun getAllClusterDetails(id:Int): Response<Cluster> {
        return apiService.getAllCluster(ClusterInput(id))
    }

    override suspend fun getFormTypeList(): Response<ActivitiesType> {
        return apiService.getFormTypeList()
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

    override suspend fun saveSurveyData(input: SurveyIn): Response<SurveyResponse> {
        return apiService.saveSurveyData(input)
    }

}