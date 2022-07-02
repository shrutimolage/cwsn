package com.cwsn.mobileapp.local.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by  on 22,June,2022
 **/
@Entity
data class AllQuestion (
    @NonNull @ColumnInfo var schoolId:Int,
    @NonNull @ColumnInfo var serverQuestId:Int,
    @NonNull @ColumnInfo var question: String?,
    @NonNull @ColumnInfo var questionFormat: String?,
    @NonNull @ColumnInfo var questionType: String?,
    @NonNull @ColumnInfo var fieldRequired: Boolean,
    @NonNull @ColumnInfo var questionFilePath: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
