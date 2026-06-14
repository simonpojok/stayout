package com.example.stayout.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.R
import com.example.stayout.presentation.preview.PreviewData.previewProperty
import com.example.stayout.presentation.preview.PreviewData.previewPropertyNoFeature
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import java.math.RoundingMode

@Composable
fun PropertyCard(
    property: PropertyDomainModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardDescription =
        stringResource(
            R.string.cd_property_card_format,
            property.name,
            "%.1f".format(property.rating),
        )
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8)
                .semantics { contentDescription = cardDescription }
                .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column {
            Box {
                NetworkImage(
                    model = property.thumbnailUrl,
                    contentDescription = stringResource(R.string.cd_property_image_format, property.name),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(Dimens.spacing180),
                    contentScale = ContentScale.Crop,
                )
                if (property.isFeatured) {
                    FeaturedBadge(
                        modifier =
                            Modifier
                                .align(Alignment.TopStart)
                                .padding(Dimens.spacing8),
                    )
                }
            }
            Column(modifier = Modifier.padding(Dimens.spacing12)) {
                Text(
                    text = property.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                StarRating(rating = property.rating, ratingCount = property.ratingCount)
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spacing6)) {
                    PropertyTypeBadge(type = property.type)
                    if (property.freeCancellationAvailable) {
                        FreeCancellationBadge()
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                Text(
                    text =
                        stringResource(
                            R.string.property_card_price_format,
                            property.lowestPriceValue.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                        ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun PropertyTypeBadge(type: String) {
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(horizontal = Dimens.spacing8, vertical = Dimens.spacing4),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun FreeCancellationBadge() {
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.tertiaryContainer,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Dimens.spacing8, vertical = Dimens.spacing4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing4),
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(Dimens.spacing12),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Text(
                text = stringResource(R.string.badge_free_cancellation),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun PropertyCardFeaturedPreview() {
    StayScoutTheme {
        PropertyCard(
            property = previewProperty,
            onClick = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyCardDefaultPreview() {
    StayScoutTheme {
        PropertyCard(
            property = previewPropertyNoFeature,
            onClick = {},
        )
    }
}
