package com.cwsn.mobileapp.model.task

import com.google.gson.annotations.SerializedName

data class AllTaskList(@SerializedName("status"  ) var status  : Boolean?        = null,
                       @SerializedName("data"    ) var data    : ArrayList<TaskData>? = null,
                       @SerializedName("message" ) var message : String?         = null
)
