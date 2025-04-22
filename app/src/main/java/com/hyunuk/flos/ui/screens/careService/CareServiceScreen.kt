package com.hyunuk.flos.ui.screens.careService

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hyunuk.flos.ui.components.AutoSlidingImageCarousel

@Composable
fun CareServiceScreen(navController: NavController) {
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ){
        item{
            //상단 배너
            ServiceBanner()
        }
        item{
            //서비스 목록
            ServiceInfo()
        }
    }
}

@Composable
fun ServiceBanner(){
    AutoSlidingImageCarousel(
        imageResList = listOf()
    ) {

    }
}

@Composable
fun ServiceInfo(){
    Text("serviceInfo")
}