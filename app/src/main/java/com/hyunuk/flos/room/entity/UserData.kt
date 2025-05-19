package com.hyunuk.flos.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyunuk.flos.model.UserInfoData

@Entity(tableName = "user")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val userInfo : UserInfoData
)
