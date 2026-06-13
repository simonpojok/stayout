package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.PropertyDataModel
import com.example.stayout.domain.model.PropertyDomainModel
import java.math.BigDecimal

class PropertyToDomainMapper(
    private val facilityCategoryMapper: FacilityCategoryToDomainMapper,
    private val ratingBreakdownMapper: RatingBreakdownToDomainMapper,
    private val promotionMapper: PromotionToDomainMapper,
) : BaseDataToDomainMapper<PropertyDataModel, PropertyDomainModel> {
    override fun map(model: PropertyDataModel): PropertyDomainModel =
        PropertyDomainModel(
            id = model.id,
            name = model.name,
            isFeatured = model.isFeatured,
            rating = model.overallRating.overall / 10.0,
            ratingCount = model.overallRating.numberOfRatings,
            lowestPriceValue = model.lowestPricePerNight.value.toBigDecimalOrNull() ?: BigDecimal.ZERO,
            lowestPriceCurrency = model.lowestPricePerNight.currency,
            overview = model.overview,
            thumbnailUrl = model.imagesGallery.firstOrNull()?.toUrl(),
            imageUrls = model.imagesGallery.map { it.toUrl() },
            address =
                buildString {
                    append(model.address1)
                    if (!model.address2.isNullOrBlank()) append(", ${model.address2}")
                },
            type = model.type,
            facilities = model.facilities.map { facilityCategoryMapper.map(it) },
            freeCancellationAvailable = model.freeCancellationAvailable,
            latitude = model.latitude,
            longitude = model.longitude,
            ratingBreakdown = model.ratingBreakdown?.let { ratingBreakdownMapper.map(it) },
            distanceKm = model.distance?.value,
            isNew = model.isNew,
            veryPopular = model.veryPopular,
            dormPriceValue = model.lowestDormPricePerNight?.value?.toBigDecimalOrNull(),
            privatePriceValue = model.lowestPrivatePricePerNight?.value?.toBigDecimalOrNull(),
            promotions = model.promotions.map { promotionMapper.map(it) },
            averagePriceValue = model.lowestAveragePricePerNight?.value?.toBigDecimalOrNull(),
            originalPriceValue = model.lowestAveragePricePerNight?.original?.toBigDecimalOrNull(),
            totalDiscount =
                model.lowestAveragePricePerNight
                    ?.promotions
                    ?.totalDiscount
                    ?.toBigDecimalOrNull(),
            district = model.district?.name,
            isRecommended = model.hostelworldRecommends,
            starRating = model.starRating,
            freeCancellationUntil = model.freeCancellationAvailableUntil,
            minimumStayDescription = model.stayRuleViolations.firstOrNull()?.description,
        )
}
