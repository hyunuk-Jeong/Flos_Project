package com.hyunuk.flos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hyunuk.flos.common.NavRoutes
import com.hyunuk.flos.ui.screens.careService.CareServiceDetailScreen
import com.hyunuk.flos.ui.screens.main.MainScreen
import com.hyunuk.flos.ui.screens.profile.NoticeScreen
import com.hyunuk.flos.ui.screens.profile.ReservationHistoryScreen
import com.hyunuk.flos.ui.screens.splash.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController() // NavController 초기화

    NavHost(navController = navController, startDestination = NavRoutes.Splash.route){
        composable(NavRoutes.Splash.route){
            SplashScreen(navController)
        }
        composable(
            route = "${NavRoutes.Main.route}?tab={start}",
            arguments = listOf(
                navArgument("tab") {
                    type = NavType.StringType
                    defaultValue = "home"
                }
            )
        ) { backStackEntry ->
            val tab = backStackEntry.arguments?.getString("tab") ?: "home"
            MainScreen(navController = navController, startDestination = tab)
        }

        composable(
            route = "${NavRoutes.CareServiceDetail.route}/{route}",
            arguments = listOf(
                navArgument("route"){
                    type = NavType.StringType
                }
            )
        ){
            backStackEntry ->
            val routeParam = backStackEntry.arguments?.getString("route") ?: ""
            // 이걸 CareServiceDetailScreen 같은 곳에 넘기면 됨
            CareServiceDetailScreen(navController = navController, route = routeParam)
        }

        composable(NavRoutes.ReservationHistory.route){
            ReservationHistoryScreen(navController = navController)
        }

        composable(NavRoutes.Notice.route){
            NoticeScreen()
        }

    }
}