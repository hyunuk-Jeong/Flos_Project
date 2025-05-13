package com.hyunuk.flos.ui.screens.reservation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.room.entity.ReservationData
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.Typography
import com.hyunuk.flos.util.noRippleClickable
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TimePicker(selectedDate:String,filteredReservation: Flow<List<ReservationData>>, onTimeSelected: (String) -> Unit) {
    var selectedTime by remember { mutableStateOf("") }

    val context = LocalContext.current

    val reservations by filteredReservation.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .padding(12.sdp_w())
                .noRippleClickable { onTimeSelected("close") },
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(32.sdp_w())
            )
        }
        Text(
            "방문시간을 선택해 주세요",
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
            TimeScheduler(interval = 30, selectedTime, selectedDate, reservations) { newTime ->
                selectedTime = newTime.toString()
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
                Text(selectedTime, style = Typography.titleLarge)
                Button(
                    onClick = {
                        if (selectedTime == "") {
                            Toast.makeText(context, "시간을 선택해주세요", Toast.LENGTH_SHORT).show()
                        } else {
                            onTimeSelected(selectedTime)
                        }
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
fun TimeScheduler(
    interval: Int,
    selectedTime: String,
    selectedDate: String,
    reservations: List<ReservationData>,
    onTimeSelected: (LocalTime) -> Unit
) {
    val now = LocalTime.now()
    val isToday = selectedDate == LocalDate.now().toString()

    val startTime = LocalTime.of(9, 0)
    val endTime = LocalTime.of(21, 0)

    val timeSlots = mutableListOf<LocalTime>()
    var currentTime = startTime

    while (currentTime.isBefore(endTime) || currentTime == endTime) {
        timeSlots.add(currentTime)
        currentTime = currentTime.plusMinutes(interval.toLong())
    }

    val morningSlots = timeSlots.filter { it.hour < 12 }
    val afternoonSlots = timeSlots.filter { it.hour >= 12 }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("오전", style = Typography.titleLarge, modifier = Modifier.padding(bottom = 24.sdp_h()))
        TimeSlotGrid(slots = morningSlots, selectedTime, isToday, now, onTimeSelected,reservations)
        Spacer(modifier = Modifier.height(32.sdp_h()))
        Text("오후", style = Typography.titleLarge, modifier = Modifier.padding(bottom = 24.sdp_h()))
        TimeSlotGrid(slots = afternoonSlots, selectedTime, isToday, now, onTimeSelected,reservations)
    }

}

@Composable
fun TimeSlotGrid(
    slots: List<LocalTime>,
    selectedTime: String,
    isToday: Boolean,
    now: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    reservations: List<ReservationData>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.sdp_h()),
        horizontalArrangement = Arrangement.spacedBy(16.sdp_w())
    ) {
        items(slots.size) { index ->
            val slot = slots[index]
            val isSelected = slot.toString() == selectedTime
            val isPastTime = isToday && slot.isBefore(now)  //이미 지나간 시간 (예약 불가)
            val isReservedTime = reservations.any {it.reservationTime == "%02d:%02d".format(slot.hour, slot.minute)}       //이미 예약 되어 있는 시간 (예약 불가)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = when {
                            isPastTime || isReservedTime -> Color.Gray
                            isSelected -> DeepGreenPrimary
                            else -> Color.White
                        },
                        shape = RoundedCornerShape(50)
                    )
                    .border(
                        width = 1.sdp_w(),
                        color = when {
                            isPastTime || isSelected || isReservedTime -> Color.White
                            else -> DeepGreenPrimary
                        },
                        shape = RoundedCornerShape(50)
                    )
                    .padding(vertical = 12.sdp_h())
                    .clickable(
                        enabled = !isPastTime && !isReservedTime,
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                        onTimeSelected(slot)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d:%02d".format(slot.hour, slot.minute),
                    color = when {
                        isSelected || isPastTime || isReservedTime -> Color.White
                        else -> DeepGreenPrimary
                    }
                )
            }
        }
    }
}