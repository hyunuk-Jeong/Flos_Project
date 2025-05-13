package com.hyunuk.flos.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.driver.SupportSQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hyunuk.flos.model.ReservationServiceData
import com.hyunuk.flos.model.UserInfoData
import com.hyunuk.flos.room.dao.ReservationDao
import com.hyunuk.flos.room.entity.ReservationData
import com.hyunuk.flos.room.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@Database(entities = [ReservationData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: ReservationDatabase? = null

        fun getDatabase(context: android.content.Context): ReservationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReservationDatabase::class.java,
                    "reservation_database"
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
                        val dao = database.reservationDao()

                        val today = LocalDate.now()
                        val endDate = today.plusMonths(6)
                        val timeSlots = generateTimeSlots()
                        val random = Random.Default
                        val serviceNames =
                            listOf("웨딩 패키지", "바디 관리", "라콜린 관리", "페이셜 관리", "산전. 산후 관리")
                        val userNames = listOf("홍길동", "이몽룡", "성춘향", "변학도", "심청이", "장보고")
                        val userPhoneNumbers = listOf(
                            "010-1234-5678",
                            "010-8765-4321",
                            "010-1111-2222",
                            "010-3333-4444",
                            "010-5555-6666",
                            "010-7777-8888"
                        )

                        val uniqueReservations = mutableSetOf<Pair<String, String>>()

                        while (uniqueReservations.size < 500) {
                            val randomDate = today.plusDays(
                                random.nextLong(
                                    0,
                                    ChronoUnit.DAYS.between(today, endDate)
                                )
                            )
                            val randomTime = timeSlots[random.nextInt(timeSlots.size)]

                            val dateTimePair = Pair(randomDate.toString(), randomTime)

                            if (!uniqueReservations.contains(dateTimePair)) {
                                uniqueReservations.add(dateTimePair)

                                val reservation = ReservationData(
                                    reservationDate = randomDate.toString(),
                                    reservationTime = randomTime,
                                    serviceList = emptyList(),
                                    userInfo = UserInfoData(userName = userNames.random(), userPhoneNumber = userPhoneNumbers.random()),
                                )

                                dao.insertReservation(reservation)
                            }
                        }
                    }
                }
            }

            private fun generateTimeSlots(): List<String> {
                val slots = mutableListOf<String>()
                val formatter = DateTimeFormatter.ofPattern("HH:mm")

                var currentTime = LocalTime.of(9, 0)
                val endTime = LocalTime.of(21, 0)

                while (currentTime <= endTime) {
                    slots.add(currentTime.format(formatter))
                    currentTime = currentTime.plusMinutes(30)
                }

                return slots
            }
        }
    }

}