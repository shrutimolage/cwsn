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
    suspend fun getAllSchoolDetailCount():Response<DashboardCount>

    @POST("api/cluster_wise_school")
    suspend fun getSchoolClusterWise(@Body input:SchoolListInput):Response<SchoolList>

    @POST("api/question_list")
    suspend fun getAllSurveyServerQuestion(@Body input:QuestListInput):Response<SurveyQuestList>

    @GET("api/FormListMonitoring")
    suspend fun getAllTaskActivityList():Response<AllTaskList>

    @GET("api/school_list")
    suspend fun getAllPendingSchool():Response<PendingSchoolResp>

    @GET("api/visitedSchooList")
    suspend fun getAllVisitedSchool():Response<VisitedSchoolResp>

    @GET
    suspend fun getGoogleLocLatLng(@Url apiUrl: String):Response<LocationLatLng>

    @POST("api/school_survey")
    suspend fun saveSurveyData(@Body input:List<SurveyInput>):Response<SurveyResponse>

    companion object{
        const val LOCATION_API="api/geocode/json?"
    }
}