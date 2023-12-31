package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.*
import com.cwsn.mobileapp.model.location.LocationLatLng
import com.cwsn.mobileapp.model.login.ForgotPassword
import com.cwsn.mobileapp.model.login.LoginModel
import com.cwsn.mobileapp.model.login.ResetPassword
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
interface ApiHelper
{
    suspend fun userLogin(emailId:String,password:String): Response<LoginModel>

    suspend fun getuserProfile():Response<UserProfile>
    suspend fun getactivitiesList(id:Int):Response<Actist_Data>
    suspend fun getAllClusterDetails(id:Int):Response<Cluster>

    suspend fun getAllSchoolDetailCount():Response<DashboardCount>
    suspend fun getFormTypeList():Response<ActivitiesType>

    suspend fun getSchoolClusterWise(input: SchoolListInput):Response<SchoolList>

    suspend fun getAllSurveyServerQuestion(input: QuestListInput):Response<SurveyQuestList>

    suspend fun getGoogleLocLatLng(apiUrl: String):Response<LocationLatLng>
    suspend fun getAllTaskActivityList():Response<AllTaskList>
    suspend fun getAllPendingSchool():Response<PendingSchoolResp>
    suspend fun getAllVisitedSchool():Response<VisitedSchoolResp>
    suspend fun saveSurveyData(input:SurveyIn):Response<SurveyResponse>
    suspend fun performForgotPwd(emailId: String): Response<ForgotPassword>
    suspend fun changeUserPassword(
        token: String,
        password: String,
        confom_password: String
    ): Response<ResetPassword>
}