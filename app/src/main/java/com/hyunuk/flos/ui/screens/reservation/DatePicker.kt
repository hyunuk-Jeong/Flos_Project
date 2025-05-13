package com.hyunuk.flos.ui.screens.reservation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.LightGray
import com.hyunuk.flos.theme.Typography
import com.hyunuk.flos.util.noRippleClickable
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun DatePicker(onDateSelected: (String) -> Unit) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    var selectedDateToString =
        "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .padding(12.sdp_w())
                .noRippleClickable { onDateSelected("close") },
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(32.sdp_w())
            )
        }
        Text(
            "방문일을 선택해 주세요",
            modifier = Modifier.padding(16.sdp_w()),
            style = Typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.sdp_h()))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.sdp_w(), vertical = 4.sdp_h())
        ) {
            DateCalendar(count = 12, selectedDate) { newDate ->
                selectedDate = newDate
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 4.sdp_h(),
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.sdp_w(), vertical = 8.sdp_h()),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(selectedDateToString, style = Typography.titleLarge)
                Button(
                    onClick = {
                        onDateSelected(selectedDate.toString())
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF444444)),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 16.sdp_w(), vertical = 8.sdp_h())
                ) {
                    Text("확인", color = Color.White)
                }
            }
        }

    }
}

@Composable
fun DateCalendar(count: Int, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val months = generateMonths(count)          // 달력 보여줄 리스트(월 단위)
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(months) { yearMonth ->
            MonthSection(yearMonth, selectedDate, onDateSelected)
        }
    }
}

@Composable
fun MonthSection(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = yearMonth.format(DateTimeFormatter.ofPattern("yyyy.M")),
            style = Typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.sdp_h()),
            textAlign = TextAlign.Start
        )
        CalendarGrid(yearMonth, selectedDate, onDateSelected)
    }
}

@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7
    val today = LocalDate.now()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Box(modifier = Modifier.size(40.sdp_w()), contentAlignment = Alignment.Center) {
                    Text(
                        text = day,
                        textAlign = TextAlign.Center,
                        color = when (day) {
                            "일" -> Color.Red
                            "토" -> Color.Blue
                            else -> Color.Black
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.sdp_h())
                .background(LightGray)
        )
        Spacer(modifier = Modifier.height(10.sdp_h()))

        val totalCells = daysInMonth + firstDayOfWeek

        for (i in 0 until totalCells step 7) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in 0 until 7) {
                    val dayIndex = i + j
                    val dayNumber = dayIndex - firstDayOfWeek + 1

                    if (dayNumber in 1..daysInMonth) {
                        val currentDate = LocalDate.of(yearMonth.year, yearMonth.month, dayNumber)
                        val isSelected = currentDate == selectedDate
                        val isPast = currentDate.isBefore(today)

                        Box(
                            modifier = Modifier
                                .size(40.sdp_w())
                                .background(
                                    if (isSelected) DeepGreenPrimary else Color.White,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                                .noRippleClickable {
                                    if (!isPast) {
                                        onDateSelected(currentDate)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$dayNumber",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = when {
                                    isPast -> Color.Gray
                                    isSelected -> Color.White
                                    else -> Color.Black
                                }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(40.sdp_w()))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.sdp_h()))
        }
    }

    Spacer(modifier = Modifier.height(30.sdp_h()))
}

fun generateMonths(count: Int): List<YearMonth> {
    val currentDate = LocalDate.now()
    val months = mutableListOf<YearMonth>()

    var yearMonth = YearMonth.of(currentDate.year, currentDate.monthValue)

    repeat(count) {
        months.add(yearMonth)
        yearMonth = yearMonth.plusMonths(1)
    }

    return months
}
