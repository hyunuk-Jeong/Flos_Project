package com.hyunuk.flos.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.hyunuk.compose_sdp.sdp_h
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.common.InstagramPostData

@Composable
fun InstagramPost(post: InstagramPostData) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.sdp_h(), start=12.sdp_w(),end = 12.sdp_w())
            .size(150.sdp_w()),
        shape = RoundedCornerShape(16.sdp_w()),
        elevation = CardDefaults.cardElevation(4.sdp_w())
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                }
        ) {
            Image(
                painter = painterResource(id = post.imageRes), // 인스타그램 이미지 추가
                contentDescription = "Instagram Post",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.linkUrl))
                        context.startActivity(intent)
                    },
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )
        }
    }
}