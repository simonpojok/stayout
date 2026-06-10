package com.example.stayout.domain.model

import java.math.BigDecimal
import java.math.RoundingMode

data class PropertyDomainModel(
    val id: Int,
    val name: String,
    val isFeatured: Boolean,
    val rating: Double,
    val ratingCount: String,
    val lowestPriceValue: BigDecimal,
    val lowestPriceCurrency: String,
    val overview: String,
    val thumbnailUrl: String?,
    val address: String,
    val type: String,
    val facilities: List<FacilityCategoryDomainModel>,
    val freeCancellationAvailable: Boolean,
)

fun PropertyDomainModel.priceIn(
    currency: CurrencyDomainModel,
    rates: ExchangeRatesDomainModel,
): String {
    val converted =
        when (currency) {
            CurrencyDomainModel.EUR -> lowestPriceValue
            CurrencyDomainModel.USD -> lowestPriceValue.multiply(rates.usd)
            CurrencyDomainModel.GBP -> lowestPriceValue.multiply(rates.gbp)
        }.setScale(2, RoundingMode.HALF_UP)
    return "${currency.symbol}$converted"
}
