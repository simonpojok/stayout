package com.example.stayout.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
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
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8)
                .semantics { contentDescription = cardDescription }
                .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.spacing2),
    ) {
        Column {
            Box {
                AsyncImage(
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
                                .align(Alignment.TopEnd)
                                .padding(Dimens.spacing8),
                    )
                }
                RatingBadge(
                    rating = property.rating,
                    modifier =
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(Dimens.spacing8),
                )
            }

            Column(modifier = Modifier.padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing12)) {
                Text(
                    text = property.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                Text(
                    text =
                        stringResource(
                            R.string.property_card_price_format,
                            property.lowestPriceValue.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                        ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(Dimens.spacing6))
                Text(
                    text =
                        HtmlCompat
                            .fromHtml(
                                property.overview,
                                HtmlCompat.FROM_HTML_MODE_COMPACT,
                            ).toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
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
