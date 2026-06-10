package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.PropertyDataModel
import com.example.stayout.domain.model.PropertyDomainModel
import java.math.BigDecimal

class PropertyToDomainMapper(
    private val facilityCategoryMapper: FacilityCategoryToDomainMapper,
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
            address =
                buildString {
                    append(model.address1)
                    if (!model.address2.isNullOrBlank()) append(", ${model.address2}")
                },
            type = model.type,
            facilities = model.facilities.map { facilityCategoryMapper.map(it) },
            freeCancellationAvailable = model.freeCancellationAvailable,
        )
}
