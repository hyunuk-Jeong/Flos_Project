package com.hyunuk.flos.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyunuk.flos.model.ReservationServiceData
import com.hyunuk.flos.model.UserInfoData

@Entity(tableName = "reservations")
data class ReservationData(
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val reservationDate : String,   // yyyy-MM-dd
    val reservationTime : String,   // HH:mm
    val serviceList : List<ReservationServiceData>,
    @Embedded val userInfo : UserInfoData,
)
