package com.hyunuk.flos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hyunuk.flos.room.dao.ReservationDao
import com.hyunuk.flos.room.entity.ReservationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoomReservationViewModel @Inject constructor(
    private val dao:ReservationDao
):ViewModel() {
    val reservations: StateFlow<List<ReservationData>> = dao.getAllReservations()
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(), emptyList())

    fun getReservationByDate(date:String): Flow<List<ReservationData>> {
        return dao.getReservationsByDate(date)
    }

    fun getReservationByUserId(id:Int): Flow<List<ReservationData>> {
        return dao.getReservationsByUserId(id)
    }

    fun addReservation(reservation: ReservationData) {
        viewModelScope.launch {
            dao.insertReservation(reservation)
        }
    }

    fun deleteReservation(reservation: ReservationData) {
        viewModelScope.launch {
            dao.deleteReservation(reservation)
        }
    }
}