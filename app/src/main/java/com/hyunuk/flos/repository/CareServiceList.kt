package com.hyunuk.flos.repository

import com.hyunuk.flos.R
import com.hyunuk.flos.model.CareServiceData

val CareServiceList:List<CareServiceData> = listOf(
    CareServiceData(route = "wedding", imageRes = R.drawable.ic_service_wedding, content = "웨딩 패키지"),
    CareServiceData(route = "body", imageRes = R.drawable.ic_service_body,content = "바디 관리"),
    CareServiceData(route = "lacolline", imageRes = R.drawable.ic_service_lacolline,content = "라콜린 관리"),
    CareServiceData(route = "face", imageRes = R.drawable.ic_service_face,content = "페이셜 관리"),
    CareServiceData(route = "maternity", imageRes = R.drawable.ic_service_maternity,content = "산전 . 산후 관리"),
)