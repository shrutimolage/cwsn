package com.cwsn.mobileapp.repository.impl

import com.cwsn.mobileapp.model.profile.UserProfile
import com.cwsn.mobileapp.network.ApiHelper
import retrofit2.Response

/**
Created by  on 29,June,2022
 **/
class ProfileRepository(private val apiHelper: ApiHelper)
{
    suspend fun fetchUserProfile(): Response<UserProfile> {
        return apiHelper.getuserProfile()
    }
}