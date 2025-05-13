package com.hyunuk.flos.di

import android.content.Context
import androidx.room.Room
import com.hyunuk.flos.room.dao.ReservationDao
import com.hyunuk.flos.room.database.ReservationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideReservationDatabase(@ApplicationContext context: Context): ReservationDatabase {
        return ReservationDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideReservationDao(database: ReservationDatabase):ReservationDao {
        return database.reservationDao()
    }
}