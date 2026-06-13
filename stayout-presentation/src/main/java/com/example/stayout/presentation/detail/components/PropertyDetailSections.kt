package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.components.SectionDivider
import com.example.stayout.presentation.detail.PropertyDetailIntent
import com.example.stayout.presentation.detail.PropertyDetailState
import com.example.stayout.presentation.detail.comments.CommentsSection
import com.example.stayout.presentation.theme.Dimens

@Composable
internal fun PropertyDetailSections(
    state: PropertyDetailState.Success,
    onIntent: (PropertyDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val property = state.property

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        PropertyImageCarousel(
            name = property.name,
            imageUrls = property.imageUrls,
            isFeatured = property.isFeatured,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing260),
        )

        Column(modifier = Modifier.padding(Dimens.spacing16)) {
            PropertyHeaderSection(
                property = property,
                modifier = Modifier.fillMaxWidth(),
            )

            property.minimumStayDescription?.let { description ->
                Spacer(modifier = Modifier.height(Dimens.spacing12))
                MinimumStayBanner(description = description, modifier = Modifier.fillMaxWidth())
            }

            SectionDivider()

            PricePerNightSection(
                property = property,
                selectedCurrency = state.selectedCurrency,
                rates = state.rates,
                ratesUnavailable = state.ratesUnavailable,
                onCurrencySelect = { onIntent(PropertyDetailIntent.SelectCurrency(it)) },
                modifier = Modifier.fillMaxWidth(),
            )

            if (property.freeCancellationAvailable) {
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                FreeCancellationBanner(
                    freeCancellationUntil = property.freeCancellationUntil,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            SectionDivider()

            PropertyOverviewSection(overview = property.overview, modifier = Modifier.fillMaxWidth())

            property.ratingBreakdown?.let { breakdown ->
                SectionDivider()
                RatingBreakdownSection(breakdown = breakdown, modifier = Modifier.fillMaxWidth())
            }

            if (property.facilities.isNotEmpty()) {
                SectionDivider()
                PropertyFacilitiesSection(facilities = property.facilities, modifier = Modifier.fillMaxWidth())
            }

            SectionDivider()

            PropertyLocationSection(
                latitude = property.latitude,
                longitude = property.longitude,
                modifier = Modifier.fillMaxWidth(),
            )

            SectionDivider()

            CommentsSection(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(Dimens.spacing32))
        }
    }
}
