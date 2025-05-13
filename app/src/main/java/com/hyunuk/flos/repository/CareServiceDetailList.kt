package com.hyunuk.flos.repository

import com.hyunuk.flos.R
import com.hyunuk.flos.model.CareServiceDetailData

val CareServiceDetailList : List<CareServiceDetailData> = listOf(
    CareServiceDetailData(
        route = "wedding",
        imageRes = R.drawable.ic_service_wedding_detail,
        title = "웨딩 패키지",
        content = "웨딩 5주 패키지 프로그램\n" +
                "\n" +
                "바디 프로그램\n" +
                "1주 전신바디 85분 + 상체 디톡스 Mask\n" +
                "2주 등 + 목,어깨 + 팔 관리\n" +
                "3주 전신바디 105분\n" +
                "4주 등 + 목,어깨 + 팔 관리\n" +
                "5주 와인필 +미니F\n" +
                "\n" +
                "페이셜 프로그램\n" +
                "1주 작은 얼굴 관리 등 & 페이셜\n" +
                "2주 콜라겐 관리 등 & 페이셜\n" +
                "3주 작은 얼굴 관리 페이셜\n" +
                "4주 작은 얼굴 관리 페이셜\n" +
                "5주 콜라겐 관리 + 상체 와인필"
    ),
    CareServiceDetailData(
        route = "body",
        imageRes = R.drawable.ic_service_body_detail,
        title = "바디 관리",
        content = "일상생활에 지친 신체의 피로를 풀어주고 문제 부위를 집중 관리해 통증을 덜어주는 개인별 맞춤 바디 관리"
    ),
    CareServiceDetailData(
        route = "lacolline",
        imageRes = R.drawable.ic_service_lacolline_detail,
        title = "라콜린 관리",
        content = "스위스 브랜드 라콜린\n" +
                "\n" +
                "CMAge Complex 이 독자적인 성분이 모든 연령대를 아울러 피부에 생기와 활력을 부여합니다.\n" +
                "세계적으로 인정받은 세포 생물학 연구 성과와 스위스 알프스의 풍부한 자연 자원을 결합하여 가장 스위스 다운 제품을 만듭니다."
    ),
    CareServiceDetailData(
        route = "face",
        imageRes = R.drawable.ic_service_face_detail,
        title = "페이셜 관리",
        content = "근막 테크닉과 얼굴 수기 & 도구 관리로 매끈한 얼굴 라인을 가질 수 있게 도와주는 프로그램\n" +
                "\n" +
                "매트리콜 콜라겐 마스크를 이용하여 진피 활성 촉진을 통해 미백, 주름감소, 영양공급을 도와주는 프로그램\n" +
                "\n" +
                "수분 부족으로 인한 건조증과 노화 진행을 막기 위해 피부 깊숙이 수분을 공급하는 프로그램"
    ),
    CareServiceDetailData(
        route = "maternity",
        imageRes = R.drawable.ic_service_maternity_detail,
        title = "산전 . 산후 관리",
        content = "출산 전 산모의 컨디션을 최대한 높여주는 프로그램\n" +
                "\n" +
                "출산 후 떨어진 기력 회복과 출산 전 바디 라인을 되찾아주는 산후 프로그램"
    ),
)