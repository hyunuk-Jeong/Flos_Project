package com.hyunuk.flos.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DeepGreenPrimary,    // 메인 딥그린 색상
    secondary = SageGreen,         // 세이지 그린 색상 (좀 더 밝고 부드러운 느낌)
    tertiary = OliveGreen,         // 올리브 그린 색상
    background = SoftBlack,       // 어두운 배경
    surface = BrownTertiary,      // 따뜻한 톤의 표면 색상
    onPrimary = White,            // 메인 텍스트 색상
    onSecondary = White,          // 선택된 아이템 텍스트 색상
    onTertiary = SoftBlack,      // 부드러운 텍스트 색상
    onBackground = White,         // 어두운 배경 위의 텍스트 색상
    onSurface = White             // 표면 위의 텍스트 색상
)

private val LightColorScheme = lightColorScheme(
    primary = DeepGreenPrimary,    // 메인 딥그린 색상
    secondary = SageGreen,         // 세이지 그린 색상
    tertiary = OliveGreen,         // 올리브 그린 색상
    background = OffWhite,         // 밝은 배경
    surface = White,               // 표면 색상
    onPrimary = White,             // 메인 텍스트 색상
    onSecondary = SoftBlack,       // 선택된 아이템 텍스트 색상
    onTertiary = SoftBlack,       // 부드러운 텍스트 색상
    onBackground = SoftBlack,     // 밝은 배경 위의 텍스트 색상
    onSurface = SoftBlack         // 표면 위의 텍스트 색상
)



@Composable
fun flosTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}