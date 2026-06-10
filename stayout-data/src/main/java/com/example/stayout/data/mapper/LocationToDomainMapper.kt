package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.LocationDataModel
import com.example.stayout.domain.model.LocationDomainModel

class LocationToDomainMapper : BaseDataToDomainMapper<LocationDataModel, LocationDomainModel> {
    override fun map(model: LocationDataModel): LocationDomainModel =
        LocationDomainModel(
            cityName = model.city.name,
            countryName = model.city.country,
        )
}
