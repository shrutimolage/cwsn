package com.cwsn.mobileapp.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
Created by  on 22,June,2022
 **/
@Entity
data class MCQOptions(
    @ColumnInfo var questId: Int,
    @ColumnInfo var optionPosition:Int,
    @ColumnInfo var option: String?)
{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @Ignore var isSelected:Boolean = false
}

