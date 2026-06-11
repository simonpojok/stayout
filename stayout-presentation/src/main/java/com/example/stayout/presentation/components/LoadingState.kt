package com.example.stayout.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 900),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "shimmerAlpha",
    )

    LazyColumn(modifier = modifier, userScrollEnabled = false) {
        items(4) {
            ShimmerCard(alpha = alpha)
        }
    }
}

fun LazyListScope.loadingMoreItems(count: Int = 2) {
    items(count) {
        ShimmerCardStatic()
    }
}

@Composable
private fun ShimmerCardStatic() {
    val transition = rememberInfiniteTransition(label = "shimmerMore")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 900),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "shimmerMoreAlpha",
    )
    ShimmerCard(alpha = alpha)
}

@Composable
internal fun ShimmerCard(alpha: Float) {
    val shimmerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(Dimens.spacing180)
                        .background(shimmerColor),
            )
            Column(modifier = Modifier.padding(Dimens.spacing12)) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.65f)
                            .height(Dimens.spacing20)
                            .background(shimmerColor, shape = MaterialTheme.shapes.extraSmall),
                )
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.35f)
                            .height(Dimens.spacing16)
                            .background(shimmerColor, shape = MaterialTheme.shapes.extraSmall),
                )
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(Dimens.spacing14)
                            .background(shimmerColor, shape = MaterialTheme.shapes.extraSmall),
                )
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.8f)
                            .height(Dimens.spacing14)
                            .background(shimmerColor, shape = MaterialTheme.shapes.extraSmall),
                )
            }
        }
    }
}

@PreviewThemes
@Composable
private fun LoadingStatePreview() {
    StayScoutTheme {
        LoadingState()
    }
}
