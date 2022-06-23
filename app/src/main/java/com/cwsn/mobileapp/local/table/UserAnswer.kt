package com.cwsn.mobileapp.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by  on 23,June,2022
 **/
@Entity
data class UserAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val question: String?,
    @ColumnInfo val questionType: String?,
    @ColumnInfo val userAnswer: String?
)