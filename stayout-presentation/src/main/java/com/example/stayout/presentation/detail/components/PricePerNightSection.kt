package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.R
import com.example.stayout.presentation.preview.PreviewData
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import java.math.BigDecimal

@Composable
internal fun PricePerNightSection(
    property: PropertyDomainModel,
    selectedCurrency: CurrencyDomainModel,
    rates: ExchangeRatesDomainModel,
    ratesUnavailable: Boolean,
    onCurrencySelect: (CurrencyDomainModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.label_price_per_night),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(Dimens.spacing10))
        if (property.promotions.isNotEmpty()) {
            Text(
                text = stringResource(R.string.label_active_deals),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing4))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8)) {
                items(property.promotions) { promotion ->
                    PromotionChip(promotion = promotion)
                }
            }
            Spacer(modifier = Modifier.height(Dimens.spacing8))
        }
        PropertyPriceSummary(
            property = property,
            selectedCurrency = selectedCurrency,
            rates = rates,
            ratesUnavailable = ratesUnavailable,
            onCurrencySelect = onCurrencySelect,
        )
        if (property.dormPriceValue != null || property.privatePriceValue != null) {
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            PropertyRoomTypePricing(
                dormPriceValue = property.dormPriceValue,
                privatePriceValue = property.privatePriceValue,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun PricePerNightSectionRegularPreview() {
    StayScoutTheme {
        PricePerNightSection(
            property = PreviewData.previewProperty,
            selectedCurrency = CurrencyDomainModel.EUR,
            rates = ExchangeRatesDomainModel(usd = BigDecimal("1.09"), gbp = BigDecimal("0.86")),
            ratesUnavailable = false,
            onCurrencySelect = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PricePerNightSectionDiscountPreview() {
    StayScoutTheme {
        PricePerNightSection(
            property =
                PreviewData.previewProperty.copy(
                    originalPriceValue = BigDecimal("20.00"),
                    averagePriceValue = BigDecimal("14.18"),
                    totalDiscount = BigDecimal("5.82"),
                ),
            selectedCurrency = CurrencyDomainModel.EUR,
            rates = ExchangeRatesDomainModel(usd = BigDecimal("1.09"), gbp = BigDecimal("0.86")),
            ratesUnavailable = false,
            onCurrencySelect = {},
        )
    }
}
