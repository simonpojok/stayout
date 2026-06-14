package com.example.stayout.presentation.model

import androidx.compose.runtime.Immutable
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import java.math.BigDecimal
import java.math.RoundingMode

@Immutable
data class PropertyUiModel(
    val id: Int,
    val name: String,
    val isFeatured: Boolean,
    val formattedRating: String,
    val ratingCount: String,
    val lowestPriceEur: BigDecimal,
    val formattedBasePrice: String,
    val overview: String,
    val thumbnailUrl: String?,
    val address: String,
    val type: String,
    val facilities: List<FacilityCategoryUiModel>,
    val freeCancellationAvailable: Boolean,
)

fun PropertyUiModel.priceIn(
    currency: CurrencyDomainModel,
    rates: ExchangeRatesDomainModel,
): String {
    val converted =
        when (currency) {
            CurrencyDomainModel.EUR -> lowestPriceEur
            CurrencyDomainModel.USD -> lowestPriceEur.multiply(rates.usd)
            CurrencyDomainModel.GBP -> lowestPriceEur.multiply(rates.gbp)
        }.setScale(2, RoundingMode.HALF_UP)
    return "${currency.symbol}$converted"
}
