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
    val imageUrls: List<String> = emptyList(),
    val address: String,
    val type: String,
    val facilities: List<FacilityCategoryDomainModel>,
    val freeCancellationAvailable: Boolean,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val ratingBreakdown: RatingBreakdownDomainModel? = null,
    val distanceKm: Double? = null,
    val isNew: Boolean = false,
    val veryPopular: Boolean = false,
    val dormPriceValue: BigDecimal? = null,
    val privatePriceValue: BigDecimal? = null,
    val promotions: List<PromotionDomainModel> = emptyList(),
    val averagePriceValue: BigDecimal? = null,
    val originalPriceValue: BigDecimal? = null,
    val totalDiscount: BigDecimal? = null,
    val district: String? = null,
    val isRecommended: Boolean = false,
    val starRating: Int = 0,
    val freeCancellationUntil: String? = null,
    val minimumStayDescription: String? = null,
    val lastUpdatedAt: Long? = null,
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

fun BigDecimal.formatIn(
    currency: CurrencyDomainModel,
    rates: ExchangeRatesDomainModel,
): String {
    val converted =
        when (currency) {
            CurrencyDomainModel.EUR -> this
            CurrencyDomainModel.USD -> this.multiply(rates.usd)
            CurrencyDomainModel.GBP -> this.multiply(rates.gbp)
        }.setScale(2, RoundingMode.HALF_UP)
    return "${currency.symbol}$converted"
}
