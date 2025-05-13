package com.hyunuk.flos.ui.screens.careService

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.repository.CareServiceDetailList
import com.hyunuk.flos.theme.Typography

@Composable
fun CareServiceDetailScreen(navController: NavController, route:String) {
    val detail = CareServiceDetailList.find { it.route == route }

    Scaffold { paddingValues ->
        if (detail == null) {
            Text("해당 정보를 찾을 수 없습니다.", modifier = Modifier.padding(paddingValues))
            return@Scaffold
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            item {
                Image(
                    painter = painterResource(id = detail.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.sdp_h()),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.sdp_h()))

                Text(
                    text = detail.title,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.sdp_w()),
                    style = Typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.sdp_h()))

                Text(
                    text = detail.content,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.sdp_w()),
                    style = Typography.bodyMedium
                )
            }
        }
    }
}