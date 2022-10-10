package com.cwsn.mobileapp.model.location

import com.google.gson.annotations.SerializedName

data class PlusCode(@SerializedName("compound_code" ) var compoundCode : String? = null,
                    @SerializedName("global_code"   ) var globalCode   : String? = null)
