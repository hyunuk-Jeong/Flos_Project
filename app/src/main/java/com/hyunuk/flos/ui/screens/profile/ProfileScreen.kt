package com.hyunuk.flos.ui.screens.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.common.NavRoutes
import com.hyunuk.flos.model.UserInfoData
import com.hyunuk.flos.room.entity.UserData
import com.hyunuk.flos.theme.DeepGreenPrimary
import com.hyunuk.flos.util.PhoneNumberTransformation
import com.hyunuk.flos.viewmodel.RoomUserViewModel
import java.io.InputStream

@Composable
fun ProfileScreen(navController: NavController,roomUserViewModel: RoomUserViewModel = hiltViewModel()) {
    val user by roomUserViewModel.userData.collectAsState(initial = UserData(userInfo = UserInfoData()))

    val userInfo = user.userInfo

    var showPrivacyPolicy by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.sdp_w(), vertical = 24.sdp_h()),
            verticalArrangement = Arrangement.spacedBy(16.sdp_h())
        ) {
            // 사용자 정보 섹션
            UserProfileSection(
                profileImageUrl = userInfo.userProfile,
                nickname = userInfo.userName,
                phoneNumber = userInfo.userPhoneNumber
            ){ profileImageUrl ->
                roomUserViewModel.updateUser(user.copy(userInfo = user.userInfo.copy(userProfile = profileImageUrl.toString())))

            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // 설정 섹션
            SettingItem(text = "내 예약목록") {
                navController.navigate(NavRoutes.ReservationHistory.route)
            }
            SettingItem(text = "개인정보 처리방침") {
                showPrivacyPolicy = true
            }
            SettingItem(text = "공지사항") {
                navController.navigate(NavRoutes.Notice.route)
            }
            SettingItem(text = "앱 버전: 1.0.0") {}

            if(showPrivacyPolicy){
                PrivacyPolicyDialog{
                    showPrivacyPolicy = false
                }
            }
        }
    }
}
@Composable
fun PrivacyPolicyDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("확인", color = DeepGreenPrimary)
            }
        },
        title = { Text("개인정보 처리방침", fontSize = 18.sp, color = Color.Black) },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.sdp_h())
                    .padding(8.sdp_w())
            ) {
                item {
                    Text(
                        "본 앱은 사용자 개인정보를 안전하게 보호하기 위해 최선을 다합니다.\n\n" +
                                "1. 수집하는 개인정보 항목\n" +
                                "- 이름, 전화번호, 이메일 주소 등\n\n" +
                                "2. 개인정보의 수집 및 이용 목적\n" +
                                "- 서비스 제공 및 사용자 맞춤형 서비스 제공\n\n" +
                                "3. 개인정보 보유 및 이용 기간\n" +
                                "- 서비스 종료 시점까지 또는 사용자의 요청에 따른 삭제 시까지\n\n" +
                                "기타 자세한 내용은 고객센터로 문의해주세요.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    )
}

@Composable
fun UserProfileSection(
    profileImageUrl: String?,
    nickname: String,
    phoneNumber: String,
    onProfileImageChanged: (Uri) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            // URI 권한 영속화
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // 업데이트
            onProfileImageChanged(it)
        }
    }

    val transformation = PhoneNumberTransformation()
    val transformedText = transformation.filter(AnnotatedString(phoneNumber)).text

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.sdp_w())
    ) {
        Box(
            modifier = Modifier
                .size(88.sdp_w())  // 기존 크기보다 약간 크게 설정
                .padding(4.dp),    // 패딩 추가
            contentAlignment = Alignment.Center
        ) {
            // 프로필 이미지 영역
            Box(
                modifier = Modifier
                    .size(80.sdp_w())
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
            ) {
                val bitmap = when {
                    imageUri != null -> getBitmapFromUri(context, imageUri!!)
                    !profileImageUrl.isNullOrEmpty() -> getBitmapFromUri(context, Uri.parse(profileImageUrl))
                    else -> null
                }

                bitmap?.let {
                    androidx.compose.foundation.Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            // 카메라 아이콘
            Box(
                modifier = Modifier
                    .size(26.sdp_w())
                    .background(Color.White, CircleShape)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Change Profile Image",
                    tint = Color.Gray,
                    modifier = Modifier.size(26.sdp_w()).padding(2.sdp_w())
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.sdp_h())
        ) {
            Text(text = nickname, color = Color.Black, fontSize = 16.sp)
            Text(text = transformedText, color = Color.Gray, fontSize = 14.sp)
        }
    }
}


private fun getBitmapFromUri(context: android.content.Context, uri: Uri): android.graphics.Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun SettingItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.sdp_h()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = DeepGreenPrimary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController())
}