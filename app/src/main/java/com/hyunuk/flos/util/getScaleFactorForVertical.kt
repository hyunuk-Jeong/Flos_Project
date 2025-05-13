package com.hyunuk.flos.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity

// 전체 화면 높이를 구하는 함수 (status/navigation bar 숨김 상태 포함)
fun getFullScreenHeight(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val bounds = windowManager.currentWindowMetrics.bounds
        bounds.height() // statusBar, navigationBar 포함 전체 높이 (px)
    } else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getRealMetrics(metrics)
        metrics.heightPixels
    }
}

@Composable
fun getScaleFactorForVertical(context: Context, image: Painter): Float {
    val density = LocalDensity.current.density

    // 전체 화면 높이 (systemBars 숨김 여부와 관계없이 포함된 값)
    val fullScreenHeightPx = getFullScreenHeight(context)
    val fullScreenHeightDp = fullScreenHeightPx / density

    // 이미지 높이 (dp)
    val imageHeightDp = image.intrinsicSize.height / density


    return fullScreenHeightDp / imageHeightDp
}
