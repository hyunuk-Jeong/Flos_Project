package com.hyunuk.flos.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hyunuk.flos.room.entity.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user:UserData)

    @Update
    suspend fun updateUser(user:UserData)

    @Delete
    suspend fun deleteUser(user: UserData)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserData>
}