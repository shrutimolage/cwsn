package com.cwsn.mobileapp.model.task

import com.google.gson.annotations.SerializedName

data class TaskListResponse(@SerializedName("tasklist" ) var tasklist : ArrayList<Tasklist> = arrayListOf())
