package com.hyunuk.flos.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hyunuk.flos.model.UserInfoData
import com.hyunuk.flos.room.dao.UserDao
import com.hyunuk.flos.room.entity.UserData
import com.hyunuk.flos.room.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: android.content.Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .addCallback(RoomCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        //DB 초기 생성 시에만 넣을 데이터 임의 삽입
        private class RoomCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.let { database ->
                        val dao = database.userDao()

                        val user = UserData(
                            userInfo = UserInfoData(
                                userName = "홍길동",
                                userPhoneNumber = "01012345678"
                            ),
                        )

                        dao.insertUser(user)
                    }
                }
            }

        }
    }
}
