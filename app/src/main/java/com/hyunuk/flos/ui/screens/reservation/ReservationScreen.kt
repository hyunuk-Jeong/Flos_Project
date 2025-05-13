package com.hyunuk.flos.ui.screens.reservation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.model.ReservationServiceData
import com.hyunuk.flos.model.UserInfoData
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.LightGray
import com.hyunuk.flos.theme.Typography
import com.hyunuk.flos.util.PhoneNumberTransformation
import com.hyunuk.flos.viewmodel.ReservationViewModel
import com.hyunuk.flos.viewmodel.RoomReservationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    navController: NavController,
    viewModel: ReservationViewModel = viewModel(),
    roomViewModel: RoomReservationViewModel = hiltViewModel()
) {

    val reservations by roomViewModel.reservations.collectAsState(initial = emptyList())

    //bottomSheet
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetContent by remember { mutableStateOf<@Composable () -> Unit>({}) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                ReservationBanner()
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightGray)
                        .height(10.sdp_h())
                )
            }
            item {
                ReservationDateTime(
                    viewModel.selectedDate,
                    viewModel.selectedTime,
                    onDateClick = {
                        sheetContent = {
                            DatePicker { date ->
                                if (date != "close") {
                                    viewModel.selectedDate = date
                                }
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }
                        }
                        showBottomSheet = true
                    },
                    onTimeClick = {
                        val datePattern = Regex("""\d{4}-\d{2}-\d{2}""")
                        val isDateFormatCorrect = datePattern.matches(viewModel.selectedDate)

                        if (isDateFormatCorrect) {
                            sheetContent = {
                                val filteredReservations =
                                    roomViewModel.getReservationByDate(viewModel.selectedDate)

                                TimePicker(viewModel.selectedDate, filteredReservations) { time ->
                                    if (time != "close") {
                                        viewModel.selectedTime = time
                                    }
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }
                            }
                            showBottomSheet = true
                        } else {
                            Toast.makeText(context, "방문일을 먼저 선택해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightGray)
                        .height(10.sdp_h())
                )
            }
            item {
                ReservationService(
                    viewModel.selectedServices,
                    onServiceClick = {
                        sheetContent = {
                            ServicePicker(viewModel.selectedServices) { services ->
                                viewModel.selectedServices = services
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }
                        }
                        showBottomSheet = true
                    })
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightGray)
                        .height(10.sdp_h())
                )
            }
            item {
                ReservationUserInfo(viewModel.userInfo) { userInfo ->
                    viewModel.userInfo = userInfo
                }
            }
            item {
                Spacer(
                    modifier = Modifier.height(200.sdp_h())
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),

        ) {
            ReservationButton(
                onResetClick = {
                    // 초기화 로직
                    viewModel.resetData()
                },
                onCompleteClick = {
                    // 완료 로직
                    if (viewModel.selectedDate.isNotEmpty() && viewModel.selectedTime.isNotEmpty() && viewModel.selectedServices.isNotEmpty()) {
                        // 예약 완료 처리
                        //TODO RoomDB에 추가
                    } else {
                        Toast.makeText(context, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Box {
                    sheetContent()
                }

            }
        }
    }
}

@Composable
fun ReservationBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp_w())
            .background(Color.White)
    ) {
        Text("원하시는 서비스와\n날짜/시간을 선택해 주세요.", style = Typography.titleLarge)
    }
}


@Composable
fun ReservationItem(
    content: String,
    buttonContent: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(content, style = Typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepGreenPrimary,  // 배경색
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                buttonContent,
                style = Typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ReservationDateTime(
    selectedDate: String,
    selectedTime: String,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp_w())
    ) {
        Text("시간", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(15.sdp_h()))

        ReservationItem(parseDateTimeString(selectedDate, "date"), "날짜 변경", onDateClick)

        Spacer(modifier = Modifier.height(10.sdp_h()))

        ReservationItem(parseDateTimeString(selectedTime, "time"), "시간 변경", onTimeClick)
    }
}


@Composable
fun ReservationService(
    selectedServices: List<ReservationServiceData>,
    onServiceClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp_w())
    ) {
        Text("서비스", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(15.sdp_h()))

        ReservationItem(convertSelectedServiceTitle(selectedServices), "서비스 변경", onServiceClick)
    }
}

fun convertSelectedServiceTitle(selectedServices: List<ReservationServiceData>): String {
    var selectedServiceToString = "서비스를 선택해주세요"
    if (selectedServices.isNotEmpty()) {
        selectedServiceToString = selectedServices[0].subCategory +
                if (selectedServices.size > 1) " 외 ${selectedServices.size - 1}" else ""
    }
    return selectedServiceToString
}

@Composable
fun ReservationUserInfo(userInfo: UserInfoData, onUserInfoChange: (UserInfoData) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp_w())
    ) {
        Text("예약자 정보", style = Typography.titleLarge)
        Spacer(modifier = Modifier.height(24.sdp_h()))

        Text("이름", style = Typography.titleMedium)
        Spacer(modifier = Modifier.height(8.sdp_h()))
        OutlinedTextField(
            value = userInfo.userName,
            onValueChange = { name ->
                onUserInfoChange(userInfo.copy(userName = name))
            },
            placeholder = { Text("이름을 입력해주세요") },
            singleLine = true,
            shape = RoundedCornerShape(12.sdp_w()),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = DeepGreenPrimary,
                focusedBorderColor = DeepGreenPrimary,
                unfocusedBorderColor = Color.DarkGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.sdp_h())
        )

        Spacer(modifier = Modifier.height(16.sdp_h()))
        Text("연락처", style = Typography.titleMedium)
        Spacer(modifier = Modifier.height(8.sdp_h()))
        OutlinedTextField(
            value = userInfo.userPhoneNumber,
            onValueChange = { newValue ->
                val digitsOnly = newValue.filter { it.isDigit() }
                if (digitsOnly.length <= 11) {
                    onUserInfoChange(userInfo.copy(userPhoneNumber = digitsOnly))
                }
            },
            visualTransformation = PhoneNumberTransformation(),
            placeholder = { Text("연락처를 입력해주세요") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.sdp_w()),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = DeepGreenPrimary,
                focusedBorderColor = DeepGreenPrimary,
                unfocusedBorderColor = Color.DarkGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.sdp_h())
        )

    }
}

@Composable
fun ReservationButton(
    onResetClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onResetClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.sdp_w())
            ) {
                Text("초기화", style = Typography.bodyMedium, color = Color.Black)
            }

            Button(
                onClick = onCompleteClick,
                colors = ButtonDefaults.buttonColors(containerColor = DeepGreenPrimary),
                modifier = Modifier
                    .weight(2f)
                    .padding(8.sdp_w())
            ) {
                Text("완료", style = Typography.bodyMedium, color = Color.White)
            }
        }
    }
}

private fun parseDateTimeString(dateTimeString: String, type: String): String {
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

@Preview(showBackground = true, widthDp = 450, heightDp = 922)
@Composable
fun Preview() {
    ReservationButton({}) { }
}