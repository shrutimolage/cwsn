package com.cwsn.mobileapp.model.school

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SchoolData(@Expose @SerializedName("id"       ) var id       : Int?    = null,
                      @Expose @SerializedName("name"     ) var name     : String? = null,
                      @Expose @SerializedName("address"  ) var address  : String? = null,
                      @Expose @SerializedName("state"    ) var state    : String? = null,
                      @Expose @SerializedName("division" ) var division : String? = null,
                      @Expose @SerializedName("district" ) var district : String? = null,
                      @Expose @SerializedName("block"    ) var block    : String? = null,
                      @Expose @SerializedName("cluster"  ) var cluster  : String? = null,
                      @Expose @SerializedName("studentcount" ) var studentCount  : Int? = null
)
