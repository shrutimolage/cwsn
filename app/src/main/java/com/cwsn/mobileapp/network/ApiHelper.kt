package com.cwsn.mobileapp.network

import com.cwsn.mobileapp.model.home.Cluster
import com.cwsn.mobileapp.model.home.DashboardCount
import com.cwsn.mobileapp.model.location.LocationLatLng
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
import retrofit2.http.Body
import retrofit2.http.Url

/**
Created by  on 16,June,2022
 **/
interface ApiHelper
{
    suspend fun userLogin(emailId:String,password:String): Response<LoginModel>

    suspend fun getuserProfile():Response<UserProfile>

    suspend fun getAllClusterDetails():Response<Cluster>

    suspend fun getAllSchoolDetailCount():Response<DashboardCount>

    suspend fun getSchoolClusterWise(input: SchoolListInput):Response<SchoolList>

    suspend fun getAllSurveyServerQuestion(input: QuestListInput):Response<SurveyQuestList>

    suspend fun getGoogleLocLatLng(apiUrl: String):Response<LocationLatLng>
    suspend fun getAllTaskActivityList():Response<AllTaskList>
    suspend fun getAllPendingSchool():Response<PendingSchoolResp>
    suspend fun getAllVisitedSchool():Response<VisitedSchoolResp>
    suspend fun saveSurveyData(input:List<SurveyInput>):Response<SurveyResponse>
}