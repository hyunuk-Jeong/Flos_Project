package com.hyunuk.flos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.hyunuk.compose_sdp.SdpInit
import com.hyunuk.flos.navigation.AppNavGraph
import com.hyunuk.flos.theme.flosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            flosTheme {
                SdpInit(375,845) {
                    AppNavGraph()
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 845)
@Composable
fun Preview() {
    flosTheme {
        AppNavGraph()
    }
}