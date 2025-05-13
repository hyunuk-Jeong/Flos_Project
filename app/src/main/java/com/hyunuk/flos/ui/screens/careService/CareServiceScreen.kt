package com.hyunuk.flos.ui.screens.careService

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.model.CareServiceData
import com.hyunuk.flos.repository.CareServiceList
import com.hyunuk.flos.theme.Typography

@Composable
fun CareServiceScreen(navController: NavController) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item { Spacer(Modifier.height(30.sdp_h())) }
            CareServiceList.forEach {
                item {
                    //서비스 목록
                    ServiceInfoCard(navController = navController, it)

                    Spacer(Modifier.height(30.sdp_h()))
                }

            }
        }

}

@Composable
fun ServiceInfoCard(navController: NavController, careServiceData: CareServiceData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("careServiceDetail/${careServiceData.route}")
            }
            .padding(horizontal = 16.sdp_w()),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.height(150.sdp_h())) {
            // 배경 이미지
            Image(
                painter = painterResource(id = careServiceData.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 검정 오버레이
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)) // 반투명 검정
            )

            // 텍스트
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = careServiceData.content,
                    color = Color.White,
                    style = Typography.displayLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    CareServiceScreen(navController = rememberNavController())
}