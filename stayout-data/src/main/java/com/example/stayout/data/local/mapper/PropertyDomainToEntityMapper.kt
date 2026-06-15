package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.converter.encodeFacilities
import com.example.stayout.data.local.converter.encodeImageUrls
import com.example.stayout.data.local.converter.encodePromotions
import com.example.stayout.data.local.converter.encodeRatingBreakdown
import com.example.stayout.data.local.entity.PropertyEntity
import com.example.stayout.data.mapper.BaseDomainToDataMapper
import com.example.stayout.domain.model.PropertyDomainModel

class PropertyDomainToEntityMapper : BaseDomainToDataMapper<PropertyDomainModel, PropertyEntity> {
    override fun map(model: PropertyDomainModel): PropertyEntity =
        PropertyEntity(
            id = model.id,
            name = model.name,
            isFeatured = model.isFeatured,
            rating = model.rating,
            ratingCount = model.ratingCount,
            lowestPriceValue = model.lowestPriceValue.toPlainString(),
            lowestPriceCurrency = model.lowestPriceCurrency,
            overview = model.overview,
            thumbnailUrl = model.thumbnailUrl,
            address = model.address,
            type = model.type,
            facilitiesJson = encodeFacilities(model.facilities),
            freeCancellationAvailable = model.freeCancellationAvailable,
            imageUrlsJson = encodeImageUrls(model.imageUrls),
            latitude = model.latitude,
            longitude = model.longitude,
            ratingBreakdownJson = model.ratingBreakdown?.let { encodeRatingBreakdown(it) },
            distanceKm = model.distanceKm,
            isNew = model.isNew,
            veryPopular = model.veryPopular,
            dormPriceValue = model.dormPriceValue?.toPlainString(),
            privatePriceValue = model.privatePriceValue?.toPlainString(),
            promotionsJson = encodePromotions(model.promotions),
            averagePriceValue = model.averagePriceValue?.toPlainString(),
            originalPriceValue = model.originalPriceValue?.toPlainString(),
            totalDiscount = model.totalDiscount?.toPlainString(),
            district = model.district,
            isRecommended = model.isRecommended,
            starRating = model.starRating,
            freeCancellationUntil = model.freeCancellationUntil,
            minimumStayDescription = model.minimumStayDescription,
            lastUpdatedAt = model.lastUpdatedAt ?: 0L,
        )
}
