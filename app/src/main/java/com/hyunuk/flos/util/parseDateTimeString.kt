package com.hyunuk.flos.util

fun parseDateTimeString(dateTimeString: String, type: String): String {
    val datePattern = Regex("""(\d{4})-(\d{2})-(\d{2})""")
    val timePattern = Regex("""(\d{2}):(\d{2})""")

    if (type == "date") {
        return datePattern.find(dateTimeString)?.let { matchResult ->
            val (year, month, day) = matchResult.destructured
            "${year.toInt()}년 ${month.toInt()}월 ${day.toInt()}일"
        } ?: dateTimeString
    } else {
        return timePattern.find(dateTimeString)?.let { matchResult ->
            val (time, minute) = matchResult.destructured
            "${time.toInt()}시 ${minute.toInt()}분"
        } ?: dateTimeString
    }
}