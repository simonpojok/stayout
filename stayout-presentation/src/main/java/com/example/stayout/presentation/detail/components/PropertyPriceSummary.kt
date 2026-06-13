package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.model.formatIn
import com.example.stayout.domain.model.priceIn
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.CurrencySelector
import com.example.stayout.presentation.preview.PreviewData
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import java.math.BigDecimal

@Composable
internal fun PropertyPriceSummary(
    property: PropertyDomainModel,
    selectedCurrency: CurrencyDomainModel,
    rates: ExchangeRatesDomainModel,
    ratesUnavailable: Boolean,
    onCurrencySelect: (CurrencyDomainModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val originalPrice = property.originalPriceValue
    val averagePrice = property.averagePriceValue
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (originalPrice != null && averagePrice != null) {
                Text(
                    text =
                        stringResource(
                            R.string.label_original_price,
                            originalPrice.formatIn(selectedCurrency, rates),
                        ),
                    style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = averagePrice.formatIn(selectedCurrency, rates),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                property.totalDiscount?.let { discount ->
                    Text(
                        text = stringResource(R.string.label_save_amount, discount.formatIn(selectedCurrency, rates)),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            } else {
                Text(
                    text = property.priceIn(selectedCurrency, rates),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = stringResource(R.string.label_per_night),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (ratesUnavailable) {
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                Text(
                    text = stringResource(R.string.label_rates_unavailable),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        CurrencySelector(
            selectedCurrency = selectedCurrency,
            onCurrencySelect = onCurrencySelect,
            modifier = Modifier.weight(1f),
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyPriceSummaryRegularPreview() {
    StayScoutTheme {
        PropertyPriceSummary(
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
private fun PropertyPriceSummaryDiscountPreview() {
    StayScoutTheme {
        PropertyPriceSummary(
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
