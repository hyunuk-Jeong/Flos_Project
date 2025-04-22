package com.hyunuk.flos.util

import android.app.Activity
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun HideSystemUI() {
    val context = LocalContext.current
    val window = (context as? Activity)?.window ?: return

    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            it.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@Composable
fun ShowSystemUI() {
    val context = LocalContext.current
    val window = (context as? Activity)?.window ?: return

    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.insetsController?.let {
            it.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        }
    }
}