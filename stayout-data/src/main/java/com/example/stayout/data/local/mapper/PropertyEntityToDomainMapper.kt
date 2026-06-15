package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.converter.decodeFacilities
import com.example.stayout.data.local.converter.decodeImageUrls
import com.example.stayout.data.local.converter.decodePromotions
import com.example.stayout.data.local.converter.decodeRatingBreakdown
import com.example.stayout.data.local.entity.PropertyEntity
import com.example.stayout.data.mapper.BaseDataToDomainMapper
import com.example.stayout.domain.model.PropertyDomainModel
import java.math.BigDecimal

class PropertyEntityToDomainMapper : BaseDataToDomainMapper<PropertyEntity, PropertyDomainModel> {
    override fun map(model: PropertyEntity): PropertyDomainModel =
        PropertyDomainModel(
            id = model.id,
            name = model.name,
            isFeatured = model.isFeatured,
            rating = model.rating,
            ratingCount = model.ratingCount,
            lowestPriceValue = BigDecimal(model.lowestPriceValue),
            lowestPriceCurrency = model.lowestPriceCurrency,
            overview = model.overview,
            thumbnailUrl = model.thumbnailUrl,
            address = model.address,
            type = model.type,
            facilities = decodeFacilities(model.facilitiesJson),
            freeCancellationAvailable = model.freeCancellationAvailable,
            imageUrls = decodeImageUrls(model.imageUrlsJson),
            latitude = model.latitude,
            longitude = model.longitude,
            ratingBreakdown = model.ratingBreakdownJson?.let { decodeRatingBreakdown(it) },
            distanceKm = model.distanceKm,
            isNew = model.isNew,
            veryPopular = model.veryPopular,
            dormPriceValue = model.dormPriceValue?.let { BigDecimal(it) },
            privatePriceValue = model.privatePriceValue?.let { BigDecimal(it) },
            promotions = decodePromotions(model.promotionsJson),
            averagePriceValue = model.averagePriceValue?.let { BigDecimal(it) },
            originalPriceValue = model.originalPriceValue?.let { BigDecimal(it) },
            totalDiscount = model.totalDiscount?.let { BigDecimal(it) },
            district = model.district,
            isRecommended = model.isRecommended,
            starRating = model.starRating,
            freeCancellationUntil = model.freeCancellationUntil,
            minimumStayDescription = model.minimumStayDescription,
            lastUpdatedAt = model.lastUpdatedAt.takeIf { it > 0L },
        )
}
