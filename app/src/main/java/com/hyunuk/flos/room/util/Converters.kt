package com.hyunuk.flos.room.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hyunuk.flos.model.ReservationServiceData
import com.hyunuk.flos.model.UserInfoData

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromReservationServiceList(serviceList: List<ReservationServiceData>): String {
        return gson.toJson(serviceList)
    }

    @TypeConverter
    fun toReservationServiceList(data: String): List<ReservationServiceData> {
        val listType = object : TypeToken<List<ReservationServiceData>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromUserInfoData(userInfo: UserInfoData): String {
        return gson.toJson(userInfo)
    }

    @TypeConverter
    fun toUserInfoData(data: String): UserInfoData {
        return gson.fromJson(data, UserInfoData::class.java)
    }
}