package com.hyunuk.flos.ui.components

import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.R
import com.hyunuk.flos.model.PopupListData

@Composable
fun SequentialPopupDialog(
    popupList: List<PopupListData>
) {
    val context = LocalContext.current
    var currentIndex by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(true) }

    val prefs = remember {
        context.getSharedPreferences("popup_prefs", Context.MODE_PRIVATE)
    }

    //광고보지않기 선택 이력 유무 체크
    fun isSeen(index: Int): Boolean {
        return prefs.getBoolean("popup_seen_$index", false)
    }

    //광고보지않기 체크 시
    fun markAsSeen(index: Int) {
        prefs.edit().putBoolean("popup_seen_$index", true).apply()
    }

    // index 벗어나면 아무 것도 하지 않음
    if (currentIndex >= popupList.size) return

    val popup = popupList[currentIndex]

    // isActive이고 seen이 아닌 경우만 다이얼로그 보여줌
    if (showDialog && popup.isActive && !isSeen(currentIndex)) {
        PopupDialog(
            imageRes = popup.imageRes,
            onDismiss = {
                showDialog = false
                currentIndex++
                showDialog = true
            },
            onDoNotShowAgainChecked = {
                markAsSeen(currentIndex)
            }
        )
    } else {
        // 현재 index가 이미 seen이거나 비활성화된 경우 다음으로 넘어감
        LaunchedEffect(currentIndex) {
            if (!popup.isActive || isSeen(currentIndex)) {
                currentIndex++
            }
        }
    }
}

@Composable
fun PopupDialog(
    imageRes: Int,
    onDismiss: () -> Unit,
    onDoNotShowAgainChecked: () -> Unit
) {
    // 다이얼로그가 생성될 때마다 초기화됨
    var checked by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Fit
                    )
                    IconButton(
                        onClick = {
                            if (checked) {
                                onDoNotShowAgainChecked()
                                checked = false
                            }
                            onDismiss()
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(255f,255f,255f,1f))
                        .padding(horizontal = 14.sdp_w(), vertical = 6.sdp_h())
                ) {
                    CustomCheckBox(
                        label = "다시 보지 않기",
                        onCheckedChange = {
                            checked = it
                        },
                        checked = checked
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    PopupDialog(
        imageRes = R.drawable.ic_launcher_background,
        onDismiss = {},
        onDoNotShowAgainChecked = {})
}