package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.presentation.model.LocationUiModel

class LocationToPresentationMapper : BaseDomainToPresentationMapper<LocationDomainModel, LocationUiModel> {
    override fun map(model: LocationDomainModel): LocationUiModel =
        LocationUiModel(
            cityName = model.cityName,
            countryName = model.countryName,
            displayText = "${model.cityName}, ${model.countryName}",
        )
}
