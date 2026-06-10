package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.FeaturedAccent
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun FeaturedBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        color = FeaturedAccent,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Dimens.spacing8, vertical = Dimens.spacing4),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(Dimens.spacing12),
                tint = Color.White,
            )
            Spacer(modifier = Modifier.width(Dimens.spacing4))
            Text(
                text = stringResource(R.string.badge_featured),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun FeaturedBadgePreview() {
    StayScoutTheme {
        FeaturedBadge()
    }
}
