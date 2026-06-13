package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.FeaturedBadge
import com.example.stayout.presentation.theme.Dimens

@Composable
internal fun PropertyImageCarousel(
    name: String,
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    isFeatured: Boolean = false,
) {
    val urls = imageUrls.ifEmpty { listOf(null) }
    val pagerState = rememberPagerState { urls.size }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            AsyncImage(
                model = urls[page],
                contentDescription = stringResource(R.string.cd_hero_image_format, name),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        if (isFeatured) {
            FeaturedBadge(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(Dimens.spacing12),
            )
        }

        if (urls.size > 1) {
            Row(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Dimens.spacing8),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing4),
            ) {
                repeat(urls.size) { index ->
                    Box(
                        modifier =
                            Modifier
                                .size(if (index == pagerState.currentPage) Dimens.spacing8 else Dimens.spacing6)
                                .clip(CircleShape)
                                .background(
                                    if (index == pagerState.currentPage) {
                                        Color.White
                                    } else {
                                        Color.White.copy(alpha = 0.5f)
                                    },
                                ),
                    )
                }
            }
        }
    }
}
