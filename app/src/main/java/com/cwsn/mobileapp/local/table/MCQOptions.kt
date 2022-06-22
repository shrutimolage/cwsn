package com.cwsn.mobileapp.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by  on 22,June,2022
 **/
@Entity
data class MCQOptions(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val questId: Int,
    @ColumnInfo val optionPosition:Int,
    @ColumnInfo val option: String?)
