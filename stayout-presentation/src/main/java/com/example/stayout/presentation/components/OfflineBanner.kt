package com.example.stayout.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

private val BannerYellow = Color(0xFFFFC107)
private val BannerTextColor = Color(0xFF1A1A1A)

@Composable
fun OfflineBanner(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(BannerYellow)
                    .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8),
        ) {
            Icon(
                imageVector = Icons.Outlined.WifiOff,
                contentDescription = null,
                tint = BannerTextColor,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(Dimens.spacing8))
            Text(
                text = "You're offline — showing cached data",
                style = MaterialTheme.typography.labelMedium,
                color = BannerTextColor,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun OfflineBannerPreview() {
    StayScoutTheme {
        OfflineBanner(visible = true)
    }
}
