package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun StarRating(
    rating: Double,
    ratingCount: String,
    modifier: Modifier = Modifier,
) {
    val starScore = rating / 2.0
    val fullStars = starScore.toInt()
    val hasHalfStar = (starScore - fullStars) >= 0.5
    val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0
    val contentDesc = stringResource(R.string.cd_star_rating_format, "%.1f".format(starScore), ratingCount)

    Row(
        modifier = modifier.semantics { contentDescription = contentDesc },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing2),
    ) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(Dimens.spacing14),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = null,
                modifier = Modifier.size(Dimens.spacing14),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                modifier = Modifier.size(Dimens.spacing14),
                tint = MaterialTheme.colorScheme.outlineVariant,
            )
        }
        Spacer(modifier = Modifier.width(Dimens.spacing4))
        Text(
            text = "%.1f · %s".format(starScore, ratingCount),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@PreviewThemes
@Composable
private fun StarRatingFullPreview() {
    StayScoutTheme {
        StarRating(rating = 8.0, ratingCount = "234")
    }
}

@PreviewThemes
@Composable
private fun StarRatingHalfPreview() {
    StayScoutTheme {
        StarRating(rating = 7.0, ratingCount = "89")
    }
}
