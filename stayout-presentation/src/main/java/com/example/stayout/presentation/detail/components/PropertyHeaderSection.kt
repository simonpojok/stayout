package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.components.PropertyRatingRow
import com.example.stayout.presentation.preview.PreviewData
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun PropertyHeaderSection(
    property: PropertyDomainModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = property.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        PropertyRatingRow(
            rating = property.rating,
            ratingCount = property.ratingCount,
            hotelStarRating = property.starRating,
        )
        Spacer(modifier = Modifier.height(Dimens.spacing6))
        PropertyBadgesContainer(
            type = property.type,
            isNew = property.isNew,
            veryPopular = property.veryPopular,
            isRecommended = property.isRecommended,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(Dimens.spacing12))
        PropertyLocationInfo(
            address = property.address,
            district = property.district,
            distanceKm = property.distanceKm,
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyHeaderSectionPreview() {
    StayScoutTheme {
        PropertyHeaderSection(property = PreviewData.previewProperty)
    }
}

@PreviewThemes
@Composable
private fun PropertyHeaderSectionWithStarsPreview() {
    StayScoutTheme {
        PropertyHeaderSection(
            property =
                PreviewData.previewProperty.copy(
                    starRating = 4,
                    isRecommended = true,
                    veryPopular = true,
                    district = "Temple Bar",
                ),
        )
    }
}
