package com.hyunuk.flos.ui.screens.reservation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.model.ReservationServiceData
import com.hyunuk.flos.repository.ReservationServiceList
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.Typography
import com.hyunuk.flos.util.convertPriceToString
import com.hyunuk.flos.util.noRippleClickable

@Composable
fun ServicePicker(selectedServices: List<ReservationServiceData>, onServiceSelected: (List<ReservationServiceData>) -> Unit) {
    var selectedMainCategory by remember { mutableStateOf("상담 예약") }

    val mainCategories = ReservationServiceList.map { it.mainCategory }.distinct()
    val filteredServices = ReservationServiceList.filter { it.mainCategory == selectedMainCategory }

    var _selectedServices by remember { mutableStateOf(listOf<ReservationServiceData>()) }
    // selectedServices가 변경될 때만 _selectedServices를 초기화함
    LaunchedEffect(selectedServices) {
        _selectedServices = selectedServices
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .padding(12.sdp_w())
                .noRippleClickable { onServiceSelected(selectedServices) },
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(32.sdp_w())
            )
        }
        Text(
            "서비스를 선택해 주세요",
            modifier = Modifier.padding(16.sdp_w()),
            style = Typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.sdp_h()))
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Column {
                MainCategorySelector(
                    mainCategories = mainCategories,
                    selectedMainCategory = selectedMainCategory,
                    onMainCategorySelected = { selectedMainCategory = it }
                )
                Spacer(modifier = Modifier.height(16.sdp_h()))
                SubCategoryList(
                    services = filteredServices,
                    selectedServices = _selectedServices,
                    onServiceSelected = { selectedService ->
                        _selectedServices = if (_selectedServices.contains(selectedService)) {
                            _selectedServices - selectedService
                        } else {
                            _selectedServices + selectedService
                        }
                    }
                )
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
                Text(convertSelectedServiceTitle(_selectedServices), style = Typography.titleLarge)
                Button(
                    onClick = {
                        onServiceSelected(_selectedServices)
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
fun MainCategorySelector(
    mainCategories: List<String>,
    selectedMainCategory: String,
    onMainCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.sdp_w()),
    ) {
        item {
            Spacer(modifier = Modifier.width(8.sdp_w()))  // 첫 번째 아이템 왼쪽 패딩
        }

        items(mainCategories) { category ->
            val isSelected = category == selectedMainCategory

            Button(
                onClick = {
                    onMainCategorySelected(category)
                },
                colors = if (isSelected) ButtonDefaults.buttonColors(
                    backgroundColor = DeepGreenPrimary
                ) else ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(50),
            ) {
                Text(category, color = if (isSelected) Color.White else Color.Black)
            }
        }

        item {
            Spacer(modifier = Modifier.width(8.sdp_w()))  // 마지막 아이템 오른쪽 패딩
        }
    }
}

@Composable
fun SubCategoryList(
    services: List<ReservationServiceData>,
    selectedServices: List<ReservationServiceData>,
    onServiceSelected: (ReservationServiceData) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.sdp_h())
    ) {
        items(services) { service ->
            SubCategoryItem(
                service = service,
                selectedServices = selectedServices,
                onServiceSelected = onServiceSelected
            )
        }
    }
}

@Composable
fun SubCategoryItem(
    service: ReservationServiceData,
    selectedServices: List<ReservationServiceData>,
    onServiceSelected: (ReservationServiceData) -> Unit
) {
    val isSelected = selectedServices.contains(service)
    val isSaled = service.salePrice != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.sdp_w())
            .noRippleClickable {
                onServiceSelected(service)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp_h(), bottom = 16.sdp_h(), end = 16.sdp_w()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onServiceSelected(service) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF00796B),
                    uncheckedColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(8.sdp_w()))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = service.subCategory,
                    style = Typography.titleLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.sdp_h()))

                service.price?.let {
                    Text(
                        text = convertPriceToString(it),
                        style = if (isSaled) Typography.bodyMedium.copy(
                            textDecoration = TextDecoration.LineThrough
                        ) else Typography.bodyLarge,
                        color = if (isSaled) Color.DarkGray else Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(4.sdp_h()))

                service.salePrice?.let {
                    val price = service.price ?: 0
                    val discountRate = ((price - it) / price.toFloat() * 100).toInt()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${discountRate}%",
                            style = Typography.bodyLarge,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.width(8.sdp_w()))
                        Text(
                            text = convertPriceToString(it),
                            style = Typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.sdp_h()))

                Text(
                    text = service.content,
                    style = Typography.bodyMedium,
                    color = Color.Gray
                )
            }


        }
    }
}

@Preview
@Composable
fun preview123() {
    ServicePicker (selectedServices = emptyList()){ }
}