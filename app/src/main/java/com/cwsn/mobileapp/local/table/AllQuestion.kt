package com.cwsn.mobileapp.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by  on 22,June,2022
 **/
@Entity
data class AllQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val question: String?,
    @ColumnInfo val questionFormat: String?,
    @ColumnInfo val questionType: String?,
    @ColumnInfo val isRequired: Boolean,
    @ColumnInfo val questionFilePath: String?
)
