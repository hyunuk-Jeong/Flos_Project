package com.hyunuk.flos.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hyunuk.flos.room.entity.ReservationData
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert
    suspend fun insertReservation(reservation: ReservationData)

    @Update
    suspend fun updateReservation(reservation: ReservationData)

    @Delete
    suspend fun deleteReservation(reservation: ReservationData)

    @Query("SELECT * FROM reservations ORDER BY reservationDate ASC, reservationTime ASC")
    fun getAllReservations(): Flow<List<ReservationData>>

    @Query("SELECT * FROM reservations WHERE reservationDate = :date")
    fun getReservationsByDate(date: String): Flow<List<ReservationData>>


}