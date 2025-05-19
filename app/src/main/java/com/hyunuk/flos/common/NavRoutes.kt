package com.hyunuk.flos.common

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Main : NavRoutes("main")
    object CareServiceDetail : NavRoutes("careServiceDetail")
    object ReservationHistory : NavRoutes("reservationHistory")
    object Notice : NavRoutes("notice")
}