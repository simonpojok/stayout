package com.example.stayout.presentation.detail.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun PropertyDetailSkeleton(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "detailShimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 900),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "detailShimmerAlpha",
    )
    val shimmer = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
    val pill = MaterialTheme.shapes.extraSmall
    val rounded = MaterialTheme.shapes.medium

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing260)
                    .background(shimmer),
        )
        Column(modifier = Modifier.padding(Dimens.spacing16)) {
            Box(modifier = Modifier.fillMaxWidth(0.75f).height(Dimens.spacing30).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8)) {
                Box(modifier = Modifier.width(Dimens.spacing48).height(Dimens.spacing26).background(shimmer, pill))
                Box(modifier = Modifier.width(Dimens.spacing80).height(Dimens.spacing26).background(shimmer, pill))
                Box(modifier = Modifier.width(Dimens.spacing64).height(Dimens.spacing26).background(shimmer, pill))
            }
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Box(modifier = Modifier.fillMaxWidth(0.55f).height(Dimens.spacing16).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing20))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Box(modifier = Modifier.width(Dimens.spacing130).height(Dimens.spacing20).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Box(modifier = Modifier.fillMaxWidth().height(Dimens.spacing44).background(shimmer, rounded))
            Spacer(modifier = Modifier.height(Dimens.spacing14))
            Box(modifier = Modifier.width(Dimens.spacing120).height(Dimens.spacing48).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing6))
            Box(modifier = Modifier.width(Dimens.spacing64).height(Dimens.spacing14).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing20))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Box(modifier = Modifier.width(Dimens.spacing56).height(Dimens.spacing20).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing10))
            repeat(4) {
                Box(modifier = Modifier.fillMaxWidth().height(Dimens.spacing14).background(shimmer, pill))
                Spacer(modifier = Modifier.height(Dimens.spacing6))
            }
            Spacer(modifier = Modifier.height(Dimens.spacing32))
        }
    }
}

@PreviewThemes
@Composable
private fun PropertyDetailSkeletonPreview() {
    StayScoutTheme {
        PropertyDetailSkeleton()
    }
}
