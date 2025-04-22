package com.hyunuk.flos.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.hyunuk.flos.R

sealed class BottomNavItem(val route: String, val label: String, val iconResId: Int) {
    object Home : BottomNavItem("home", "홈", R.drawable.ic_home)
    object CareService : BottomNavItem("careService", "서비스", R.drawable.ic_service)
    object Reservation : BottomNavItem("reservation", "예약", R.drawable.ic_calendar)
    object Chat : BottomNavItem("chat", "상담", R.drawable.ic_chat)
    object Profile : BottomNavItem("profile", "내정보", R.drawable.ic_profile)

    companion object {
        val items = listOf(Home, CareService, Reservation, Chat, Profile)
    }
}

@Composable
fun BottomNavItemIcon(item: BottomNavItem): Painter {
    return painterResource(id = item.iconResId)
}