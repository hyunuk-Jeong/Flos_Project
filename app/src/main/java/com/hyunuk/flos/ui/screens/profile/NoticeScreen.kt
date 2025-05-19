package com.hyunuk.flos.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.model.NoticeData
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.theme.Typography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeScreen() {
    val notices = remember {
        generateDummyNotices()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text("공지사항", color = Color.White, style = Typography.titleLarge)
                },
                colors = TopAppBarDefaults.topAppBarColors(DeepGreenPrimary),


                )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.sdp_w()),
                verticalArrangement = Arrangement.spacedBy(8.sdp_h())
            ) {
                items(notices) { notice ->
                    NoticeItem(notice = notice)
                }
            }
        }
    }
}

@Composable
fun NoticeItem(notice: NoticeData) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = notice.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreenPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = notice.date,
                fontSize = 12.sp,
                color = Color.Gray
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = notice.content,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

fun generateDummyNotices(): List<NoticeData> {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = formatter.format(Date())

    return listOf(
        NoticeData(1, "점검 안내", "서버 점검이 5월 20일 오전 2시부터 4시까지 진행됩니다.", today),
        NoticeData(2, "서비스 업데이트", "앱이 최신 버전으로 업데이트되었습니다.", "2025-05-18"),
        NoticeData(3, "이용 시간 변경", "운영 시간이 오전 9시부터 오후 8시까지로 변경됩니다.", "2025-05-15"),
        NoticeData(4, "결제 오류 공지", "일부 결제 서비스에서 오류가 발생했습니다.", "2025-05-12"),
        NoticeData(5, "회원 등급제 안내", "새로운 회원 등급제가 도입되었습니다.", "2025-05-10"),
        NoticeData(6, "데이터 백업 공지", "데이터 백업이 5월 25일에 진행됩니다.", "2025-05-08"),
        NoticeData(7, "신규 서비스 런칭", "새로운 서비스 '플로스 케어'가 런칭되었습니다.", "2025-05-05"),
        NoticeData(8, "리뷰 이벤트", "리뷰 작성 이벤트가 시작되었습니다.", "2025-05-03"),
        NoticeData(9, "이용약관 개정", "이용약관이 5월 1일자로 개정되었습니다.", "2025-05-01"),
        NoticeData(10, "앱 점검 예정", "5월 30일 오전 1시부터 3시까지 앱 점검이 예정되어 있습니다.", "2025-04-30")
    )
}