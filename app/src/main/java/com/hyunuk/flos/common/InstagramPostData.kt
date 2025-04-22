package com.hyunuk.flos.common

import com.hyunuk.flos.R

data class InstagramPostData(
    val imageRes: Int, // drawable 리소스
    val linkUrl: String // 인스타그램 경로 또는 딥링크
)

val instagramPostList = listOf(
    InstagramPostData(R.drawable.ic_insta_9,"https://www.instagram.com/p/DIh99f9zltW/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_8,"https://www.instagram.com/p/DHH4u62zMkK/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_7,"https://www.instagram.com/p/DHDU9jFTqKx/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_6,"https://www.instagram.com/p/DGzQEzRzpNd/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_5,"https://www.instagram.com/p/DGsHu0rTSM9/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_4,"https://www.instagram.com/p/DGue5plTYoL/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_3,"https://www.instagram.com/p/DGpINIDTqHn/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_2,"https://www.instagram.com/p/DGnPYcvzg0t/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
    InstagramPostData(R.drawable.ic_insta_1,"https://www.instagram.com/p/DGaJglETM_c/?utm_source=ig_web_copy_link&igsh=MzRlODBiNWFlZA=="),
)