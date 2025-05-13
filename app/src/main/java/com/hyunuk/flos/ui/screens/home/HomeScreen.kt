package com.hyunuk.flos.ui.screens.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.repository.HomeBannerImageList
import com.hyunuk.flos.repository.PopupList
import com.hyunuk.flos.repository.instagramPostList
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.SageGreen
import com.hyunuk.flos.theme.Typography
import com.hyunuk.flos.theme.White
import com.hyunuk.flos.theme.flosTheme
import com.hyunuk.flos.ui.components.AutoSlidingImageCarousel
import com.hyunuk.flos.ui.components.InstagramPost
import com.hyunuk.flos.ui.components.SequentialPopupDialog
import com.hyunuk.flos.ui.screens.main.MainScreen

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

    ) {
        item {
            // 상단 배너
            HomeBanner()
        }

        item {
            Spacer(modifier = Modifier.height(8.sdp_h()))
        }

        item {
            // 설명 텍스트
            HomeDescription()
        }
        item {
            Spacer(modifier = Modifier.height(16.sdp_h()))
        }

        item {
            // 원장 소개
            HomeAbout()
        }

        item {
            Spacer(modifier = Modifier.height(32.sdp_h()))
        }

        item {
            // 인스타그램 게시물 섹션
            HomeInstagramFeed()
        }
    }
}

@Composable
fun HomeBanner() {
    val imageList = HomeBannerImageList
    AutoSlidingImageCarousel(
        imageResList = imageList,
        intervalMillis = 5000L,
    ) { page ->
        Card(
            shape = RoundedCornerShape(16.sdp_w()),
            elevation = CardDefaults.cardElevation(4.sdp_w()),
            modifier = Modifier.padding(16.sdp_w())
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = imageList[page]),
                    contentDescription = "HomeBanner",
                    modifier = Modifier.aspectRatio(16f / 15f),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillWidth
                )
                if(page % imageList.size == 0){
                    Text(
                        text = "당신을 위한 힐링 공간, \n플로스 스파",
                        color = White,
                        modifier = Modifier
                            .padding(16.sdp_w())
                            .align(Alignment.BottomStart),
                        style = Typography.titleLarge,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeDescription() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.sdp_w()),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = "도심 속 작은 쉼표, 플로스 스파.\n정교한 테라피와 차분한 공간이 당신의 하루에 깊은 휴식을 선사합니다.",
            style = Typography.bodyMedium,
            color = DeepGreenPrimary,
            modifier = Modifier.padding(bottom = 16.sdp_h()),
            textAlign = TextAlign.Center
        )

        Text(
            text = "플로스는 단순한 피부 관리가 아닌,\n전문 진단을 바탕으로 개인별 맞춤 케어를 제공합니다.",
            style = Typography.bodyMedium,
            color = DeepGreenPrimary,
            modifier = Modifier.padding(bottom = 16.sdp_h()),
            textAlign = TextAlign.Center
        )

        Text(
            text = "한 분 한 분에게 최상의 편안함과 고급스러움을 선사합니다.",
            style = Typography.bodyMedium,
            color = DeepGreenPrimary,
            modifier = Modifier.padding(bottom = 16.sdp_h()),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HomeAbout() {
    Box(modifier = Modifier.background(DeepGreenPrimary)) {
        Column(modifier = Modifier.padding(16.sdp_h())) {
            Text(
                text = "원장 소개",
                color = White,
                style = Typography.displayLarge,
                modifier = Modifier.padding(bottom = 32.sdp_h())
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.sdp_w(), bottom = 32.sdp_h()),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 왼쪽: 원장님 사진
                Image(
                    painter = painterResource(id = R.drawable.ic_director_profile), // 원장님 사진 파일
                    contentDescription = "Director",
                    modifier = Modifier
                        .size(130.sdp_w())
                        .clip(RoundedCornerShape(16.sdp_w())),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.sdp_w()))

                // 오른쪽: 이력 정보
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "김영경 원장",
                        style = Typography.titleLarge,
                        color = White
                    )

                    Spacer(modifier = Modifier.height(8.sdp_h()))

                    Text(
                        text = "・피부미용사 국가자격증 보유\n・청담 XX스파 10년 경력\n・자연주의 테라피 전문가\n・Flos 대표 테라피스트",
                        style = Typography.bodyMedium,
                        color = White,
                        lineHeight = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.sdp_h()))
            Text(
                text = "아름다움은 정성에서 시작됩니다.\n" +
                        "매 순간을 소중히, 진심을 담아 케어합니다.",
                color = White,
                style = Typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.sdp_h())
            )
        }
    }

}

@Composable
fun HomeInstagramFeed() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.sdp_h(), start = 16.sdp_w(), end = 16.sdp_w(), bottom = 8.sdp_h()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = "인스타그램",
                color = DeepGreenPrimary,
                style = Typography.displayLarge,
                modifier = Modifier.padding(bottom = 16.sdp_h())
            )
            Text(
                text = "더보기",
                color = SageGreen,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.sdp_h()).clickable {
                    val intent = Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/flos_sol_/"))
                    context.startActivity(intent)
                }
            )
        }

        LazyRow {
            items(instagramPostList){ post ->
                InstagramPost(post)
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 450, heightDp = 922)
@Composable
fun Preview() {
    flosTheme {
        MainScreen(navController = rememberNavController(), "home")
    }
}