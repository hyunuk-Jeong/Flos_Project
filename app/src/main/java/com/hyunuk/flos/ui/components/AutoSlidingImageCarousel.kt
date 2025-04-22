package com.hyunuk.flos.ui.components


import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoSlidingImageCarousel(
    imageResList : List<Int>,             //이미지 리스트
    intervalMillis : Long = 5000L,       //몇 밀리초마다 넘길지 (기본값 5초)
    onPageChanged: ((Int) -> Unit)? = null,
    content : @Composable (page: Int) -> Unit
) {
    if(imageResList.isEmpty()) return

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { imageResList.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true){
            delay(intervalMillis)
            val nextPage = (pagerState.currentPage + 1) % imageResList.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    LaunchedEffect(pagerState.currentPage){
        onPageChanged?.invoke(pagerState.currentPage)
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
        content(page)
    }

}
