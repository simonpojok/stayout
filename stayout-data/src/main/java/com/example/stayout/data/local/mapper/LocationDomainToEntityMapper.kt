package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.LocationEntity
import com.example.stayout.data.mapper.BaseDomainToDataMapper
import com.example.stayout.domain.model.LocationDomainModel

class LocationDomainToEntityMapper : BaseDomainToDataMapper<LocationDomainModel, LocationEntity> {
    override fun map(model: LocationDomainModel): LocationEntity =
        LocationEntity(
            cityName = model.cityName,
            countryName = model.countryName,
        )
}
