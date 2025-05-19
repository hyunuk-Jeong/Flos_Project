package com.hyunuk.flos.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.model.UserInfoData
import com.hyunuk.flos.room.entity.ReservationData
import com.hyunuk.flos.room.entity.UserData
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.util.PhoneNumberTransformation
import com.hyunuk.flos.util.parseDateTimeString
import com.hyunuk.flos.viewmodel.RoomReservationViewModel
import com.hyunuk.flos.viewmodel.RoomUserViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ReservationHistoryScreen(
    navController: NavController,
    roomUserViewModel: RoomUserViewModel = hiltViewModel(),
    roomReservationViewModel: RoomReservationViewModel = hiltViewModel()
) {
    val user by roomUserViewModel.userData.collectAsState(UserData(userInfo = UserInfoData()))
    val reservations by roomReservationViewModel.getReservationByUserId(user.id).collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.sdp_w(), vertical = 24.sdp_h()),
            verticalArrangement = Arrangement.spacedBy(16.sdp_h())
        ) {
            Text(
                text = "예약 목록",
                fontSize = 20.sp,
                color = DeepGreenPrimary,
                modifier = Modifier.padding(bottom = 8.sdp_h())
            )

            if (reservations.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "예약 내역이 없습니다.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.sdp_h()),
                    contentPadding = PaddingValues(bottom = 16.sdp_h())
                ) {
                    items(reservations) { reservation ->
                        ReservationHistoryItem(reservation = reservation)
                    }
                }
            }
        }
    }
}

@Composable
fun ReservationHistoryItem(reservation: ReservationData) {
    val transformation = PhoneNumberTransformation()
    val transformedText = transformation.filter(AnnotatedString(reservation.userInfo.userPhoneNumber)).text

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.sdp_w())
            .clip(RoundedCornerShape(16.sdp_w()))
            .background(Color.White)
            .border(1.sdp_w(), DeepGreenPrimary, RoundedCornerShape(16.sdp_w())),
    ) {
        Column(
            modifier = Modifier.padding(16.sdp_w()),
            verticalArrangement = Arrangement.spacedBy(8.sdp_h())
        ) {
            Text(
                text = "예약일: ${parseDateTimeString(reservation.reservationDate,"date")}",
                fontSize = 16.sp,
                color = DeepGreenPrimary
            )

            Text(
                text = "예약 시간: ${parseDateTimeString(reservation.reservationTime,"time")}",
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = "예약자: ${reservation.userInfo.userName}",
                fontSize = 14.sp,
                color = Color.Black
            )


            Text(
                text = "전화번호: $transformedText",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.sdp_h()))

            Text(
                text = "서비스 내역",
                fontSize = 14.sp,
                color = DeepGreenPrimary
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.sdp_h())) {
                reservation.serviceList.forEach { service ->

                    val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(service.salePrice ?: 0)

                    Text(
                        text = "• ${service.subCategory} (${formattedPrice}원)",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
