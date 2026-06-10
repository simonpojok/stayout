package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.R
import com.example.stayout.presentation.model.PropertyUiModel
import com.example.stayout.presentation.provider.ResourceProvider
import java.math.RoundingMode

class PropertyToPresentationMapper(
    private val resources: ResourceProvider,
    private val facilityCategoryMapper: FacilityCategoryToPresentationMapper,
) : BaseDomainToPresentationMapper<PropertyDomainModel, PropertyUiModel> {
    override fun map(model: PropertyDomainModel): PropertyUiModel =
        PropertyUiModel(
            id = model.id,
            name = model.name,
            isFeatured = model.isFeatured,
            formattedRating = "%.1f".format(model.rating),
            ratingCount = model.ratingCount,
            lowestPriceEur = model.lowestPriceValue,
            formattedBasePrice =
                resources.getString(
                    R.string.property_base_price_format,
                    CurrencyDomainModel.EUR.symbol,
                    model.lowestPriceValue.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                ),
            overview = model.overview,
            thumbnailUrl = model.thumbnailUrl,
            address = model.address,
            type = model.type,
            facilities = model.facilities.map { facilityCategoryMapper.map(it) },
            freeCancellationAvailable = model.freeCancellationAvailable,
        )
}
