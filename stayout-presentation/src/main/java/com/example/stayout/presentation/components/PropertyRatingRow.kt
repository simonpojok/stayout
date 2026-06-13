package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

/**
 * Displays the full rating block for a property:
 *
 * - [RatingBadge] with the 1–10 guest score (as per spec)
 * - Five-star visual derived from that score (primary colour)
 * - Review count
 * - Hotel star classification row (amber stars) when [hotelStarRating] > 0
 */
@Composable
fun PropertyRatingRow(
    rating: Double,
    ratingCount: String,
    modifier: Modifier = Modifier,
    hotelStarRating: Int = 0,
) {
    val starScore = rating / 2.0
    val fullStars = starScore.toInt()
    val hasHalf = (starScore - fullStars) >= 0.5
    val emptyStars = 5 - fullStars - if (hasHalf) 1 else 0
    val a11yDesc =
        stringResource(
            R.string.cd_star_rating_format,
            "%.1f".format(starScore),
            ratingCount,
        )

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing6),
            modifier = Modifier.semantics { contentDescription = a11yDesc },
        ) {
            RatingBadge(rating = rating)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing2),
            ) {
                repeat(fullStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimens.spacing14),
                    )
                }
                if (hasHalf) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.StarHalf,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimens.spacing14),
                    )
                }
                repeat(emptyStars) {
                    Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(Dimens.spacing14),
                    )
                }
            }

            if (ratingCount.isNotBlank() && ratingCount != "0") {
                Text(
                    text = "· $ratingCount reviews",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        if (hotelStarRating > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing2),
                modifier = Modifier.semantics(mergeDescendants = true) {},
            ) {
                repeat(hotelStarRating) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(Dimens.spacing14),
                    )
                }
                Spacer(modifier = Modifier.width(Dimens.spacing4))
                Text(
                    text = stringResource(R.string.label_star_rating_format, hotelStarRating),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@PreviewThemes
@Composable
private fun PropertyRatingRowHostelPreview() {
    StayScoutTheme {
        PropertyRatingRow(
            rating = 8.5,
            ratingCount = "1 234",
            hotelStarRating = 0,
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyRatingRowHotelPreview() {
    StayScoutTheme {
        PropertyRatingRow(
            rating = 9.2,
            ratingCount = "567",
            hotelStarRating = 4,
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyRatingRowNoReviewsPreview() {
    StayScoutTheme {
        PropertyRatingRow(
            rating = 7.0,
            ratingCount = "0",
            hotelStarRating = 3,
        )
    }
}
