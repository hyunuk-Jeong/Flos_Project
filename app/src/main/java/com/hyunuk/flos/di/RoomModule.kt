package com.hyunuk.flos.di

import android.content.Context
import com.hyunuk.flos.room.dao.ReservationDao
import com.hyunuk.flos.room.dao.UserDao
import com.hyunuk.flos.room.database.ReservationDatabase
import com.hyunuk.flos.room.database.UserDatabase
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

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase):UserDao {
        return database.userDao()
    }
}