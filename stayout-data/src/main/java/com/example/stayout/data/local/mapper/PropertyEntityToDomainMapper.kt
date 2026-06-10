package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.converter.decodeFacilities
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
        )
}
