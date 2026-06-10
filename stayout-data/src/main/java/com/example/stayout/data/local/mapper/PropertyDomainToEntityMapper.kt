package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.converter.encodeFacilities
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
        )
}
