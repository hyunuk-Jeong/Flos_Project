package com.hyunuk.flos.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.common.BottomNavItem
import com.hyunuk.flos.repository.PopupList
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.flosTheme
import com.hyunuk.flos.ui.components.BottomNavigationBar
import com.hyunuk.flos.ui.components.SequentialPopupDialog
import com.hyunuk.flos.ui.screens.careService.CareServiceScreen
import com.hyunuk.flos.ui.screens.chat.ChatScreen
import com.hyunuk.flos.ui.screens.home.HomeScreen
import com.hyunuk.flos.ui.screens.profile.ProfileScreen
import com.hyunuk.flos.ui.screens.reservation.ReservationScreen

/*
   화면구성
   - 홈
        - 이벤트 slideView
        - 간단한 소개
        - 오시는 길
        - 인스타 정보 연동
   - 서비스 목록
        - 각 서비스 이름, 설명, 가격
        - 선택 시 상세 화면
   - 예약
        - 예약 날짜/ 시간 입력
   - 채팅
        - 담당자와 상담
   - 프로필
        - 고객 정보 관리, 예약 내역 등
        - 로그인
        - 알림
        - 공지사항

   - etc
        - FCM 푸시
        - 결제기능
        - 네이버 예약 연동
 */

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController,startDestination:String) {
    val bottomNavController = rememberNavController()

    //순차적으로 팝업 리스트 출력
    SequentialPopupDialog(popupList = PopupList)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo_vertical),
                        contentDescription = "Home Icon",
                        modifier = Modifier
                            .size(100.sdp_h())
                            .padding(start = 12.sdp_w())
                            .clickable {
                                bottomNavController.navigate(BottomNavItem.Home.route) {
                                    bottomNavController.graph.startDestinationRoute?.let {
                                        popUpTo(it) { saveState = true }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(DeepGreenPrimary),


            )
        },
        bottomBar = {
            BottomNavigationBar(bottomNavController)
        }
    ) { innerPadding ->
        NavHost(navController = bottomNavController, modifier = Modifier.padding(innerPadding), startDestination = startDestination) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController)
            }
            composable(BottomNavItem.CareService.route) {
                CareServiceScreen(navController)
            }
            composable(BottomNavItem.Reservation.route) {
                ReservationScreen(navController)
            }
            composable(BottomNavItem.Chat.route) {
                ChatScreen(navController)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController)
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 450, heightDp = 922)
@Composable
fun Preview() {
    flosTheme {
        MainScreen(navController = rememberNavController(),"home")
    }
}