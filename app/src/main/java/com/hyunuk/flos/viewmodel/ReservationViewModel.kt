package com.hyunuk.flos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hyunuk.flos.model.ReservationServiceData
import com.hyunuk.flos.model.UserInfoData

class ReservationViewModel : ViewModel() {
    var selectedDate by mutableStateOf("방문일을 선택해주세요")
    var selectedTime by mutableStateOf("방문시간을 선택해주세요")
    var selectedServices by mutableStateOf(emptyList<ReservationServiceData>())
    var userInfo by mutableStateOf(UserInfoData())

    fun resetData() {
        selectedDate = "방문일을 선택해주세요"
        selectedTime = "방문시간을 선택해주세요"
        selectedServices = emptyList<ReservationServiceData>()
        userInfo = UserInfoData(userName = "", userPhoneNumber = "")
    }

}