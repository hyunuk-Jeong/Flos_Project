package com.hyunuk.flos.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.theme.Typography
import com.hyunuk.flos.theme.White
import com.hyunuk.flos.util.HideSystemUI
import com.hyunuk.flos.util.getScaleFactorForVertical

@Composable
fun SplashScreen(navController: NavController) {

    // 이미지 로딩 (이미지를 painter로 가져옵니다)
    val imagePainter = painterResource(id = R.drawable.ic_splash)

    // 세로 크기를 맞추기 위한 scale 값 계산
    val scale = getScaleFactorForVertical(LocalContext.current, imagePainter)

    // 이미지가 애니메이션으로 이동하는 값
    val offsetX = remember { Animatable(300f) } // 시작 위치를 왼쪽으로 설정


    // 스플래시 화면에서 2초 후 메인 화면으로 이동
    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = -300f, // 애니메이션 끝에 이미지가 이동할 위치
            animationSpec = tween(durationMillis = 2000) // 애니메이션 duration 설정
        )
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Surface(modifier = Modifier.fillMaxSize().graphicsLayer { clip=false }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().graphicsLayer { clip = false }.background(Color.Blue),
        ) {
            // 배경 이미지에 애니메이션 적용
            Image(
                contentDescription = "Splash Image",
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offsetX.value
                    },
                painter = painterResource(id = R.drawable.ic_splash), // 자신의 배경 이미지
                contentScale = ContentScale.None
            )

            // 텍스트
            Text(
                text = "당신을 위한 힐링 공간, \n플로스 스파",
                color = White,
                modifier = Modifier
                    .padding(start = 20.sdp_w(), bottom = 80.sdp_h())
                    .align(Alignment.BottomStart),
                style = Typography.displayLarge,
            )
        }
    }
}

@Preview
@Composable
fun preview(){
    SplashScreen(navController = rememberNavController())
}