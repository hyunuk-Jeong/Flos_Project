package com.hyunuk.flos.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hyunuk.compose_sdp.sdp_w
import com.hyunuk.flos.common.BottomNavItem
import com.hyunuk.flos.common.BottomNavItemIcon
import com.hyunuk.flos.theme.OffWhite
import com.hyunuk.flos.theme.OliveGreen
import com.hyunuk.flos.theme.flosTheme
import com.hyunuk.flos.ui.screens.main.MainScreen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedColor = OliveGreen        // 딥그린 포인트
    val unselectedColor = Color(0xFF888888) // 부드러운 그레이
    val backgroundColor = OffWhite           // 부드러운 흰 바탕

    BottomNavigation(
        backgroundColor = backgroundColor,
        contentColor = selectedColor,
        modifier = Modifier.navigationBarsPadding()
    ) {
        BottomNavItem.items.forEach { item ->
            val selected = currentRoute == item.route

            BottomNavigationItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = BottomNavItemIcon(item),
                        contentDescription = item.label,
                        modifier = Modifier.size(26.sdp_w()),
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                    )
                },
                alwaysShowLabel = false,
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 450, heightDp = 922)
@Composable
fun Preview() {
    flosTheme {
        MainScreen(navController = rememberNavController(), "home")
    }
}

