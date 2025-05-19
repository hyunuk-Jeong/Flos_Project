package com.hyunuk.flos.ui.screens.chat

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.theme.DeepGreenPrimary
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current  // Context 얻기

    var chatMessages = remember { mutableStateListOf(ChatMessage("원하시는 상담 내용을 입력해주세요.", false)) }
    var options = remember { mutableStateListOf("예약 문의", "서비스 안내", "이용 시간", "기타 문의") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.sdp_w(), vertical = 8.sdp_h())
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.weight(1f)
        ) {
            items(chatMessages.size) { index ->
                ChatBubble(chatMessages[index])

                // 봇 메시지 바로 아래에 옵션 버튼들 배치
                if (!chatMessages[index].isMine) {
                    // 봇 메시지가 나오고 나서 옵션 버튼 표시
                    if (index == chatMessages.lastIndex) {
                        Spacer(modifier = Modifier.height(8.sdp_h()))
                        QnAOptionsRow(
                            options = options,
                            onOptionClick = { selectedOption ->
                                chatMessages.add(ChatMessage(selectedOption, true))

                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(chatMessages.size - 1)
                                }

                                // 예시 답변 및 옵션 업데이트
                                when (selectedOption) {
                                    "예약 문의" -> {
                                        chatMessages.add(ChatMessage("예약 관련 안내입니다. 원하는 서비스를 선택해주세요.", false))
                                        options.clear()
                                        options.addAll(listOf("서비스 A", "서비스 B", "처음으로"))
                                    }
                                    "서비스 안내" -> {
                                        chatMessages.add(ChatMessage("저희 서비스에 대해 알려드릴게요.", false))
                                        options.clear()
                                        options.addAll(listOf("가격 문의", "서비스 내용", "처음으로"))
                                    }
                                    "이용 시간" -> {
                                        chatMessages.add(ChatMessage("이용 시간은 평일 오전 10시부터 오후 9시까지입니다.", false))
                                        options.clear()
                                        options.addAll(listOf("처음으로"))
                                    }
                                    "기타 문의" -> {
                                        chatMessages.add(ChatMessage("기타 문의는 고객센터로 연락주세요.", false))
                                        options.clear()
                                        options.addAll(listOf("처음으로"))
                                    }
                                    "처음으로" -> {
                                        chatMessages.add(ChatMessage("원하시는 상담 내용을 입력해주세요.", false))
                                        options.clear()
                                        options.addAll(listOf("예약 문의", "서비스 안내", "이용 시간", "기타 문의"))
                                    }
                                    "상담하기" -> {
                                        chatMessages.add(ChatMessage("상담화면으로 이동합니다.", false))

                                        //카카오톡 연동
                                        // 카카오톡 플친 링크 Intent 실행
                                        val kakaoUrl = "https://pf.kakao.com/_sxmdxbn"
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl))

                                        // 앱이 없으면 웹 브라우저에서 열림
                                        context.startActivity(intent)

                                        chatMessages.add(ChatMessage("원하시는 상담 내용을 입력해주세요.", false))
                                        options.clear()
                                        options.addAll(listOf("예약 문의", "서비스 안내", "이용 시간", "기타 문의"))
                                    }
                                    else -> {
                                        chatMessages.add(ChatMessage("선택하신 '$selectedOption' 에 대한 답변입니다.", false))
                                        options.clear()
                                        options.addAll(listOf("상담하기","처음으로"))
                                    }
                                }

                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(chatMessages.size - 1)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.sdp_h()))
                    }
                }
            }
        }
    }
}

@Composable
fun QnAOptionsRow(
    options: List<String>,
    onOptionClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.sdp_w()),
        contentPadding = PaddingValues(horizontal = 8.sdp_w())
    ) {
        items(options) { option ->
            Button(
                onClick = { onOptionClick(option) },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = DeepGreenPrimary
                ),
                shape = RoundedCornerShape(20.sdp_w()),
                modifier = Modifier
                    .height(40.sdp_h())
                    .border(
                        width = 1.dp,
                        color = DeepGreenPrimary,
                        shape = RoundedCornerShape(20.sdp_w())
                    )
                    .clip(RoundedCornerShape(20.sdp_w()))
            ) {
                Text(text = option)
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.sdp_h()),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isMine) DeepGreenPrimary else Color.LightGray,
                    shape = RoundedCornerShape(16.sdp_w())
                )
                .padding(12.sdp_w())
                .widthIn(max = 250.sdp_w())
        ) {
            Text(
                text = message.text,
                color = if (message.isMine) Color.White else Color.Black
            )
        }
    }
}

data class ChatMessage(val text: String, val isMine: Boolean)


@Preview
@Composable
fun previewChat(){
    ChatScreen(navController = rememberNavController())
}