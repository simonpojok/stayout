package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.LocationEntity
import com.example.stayout.data.mapper.BaseDataToDomainMapper
import com.example.stayout.domain.model.LocationDomainModel

class LocationEntityToDomainMapper : BaseDataToDomainMapper<LocationEntity, LocationDomainModel> {
    override fun map(model: LocationEntity): LocationDomainModel =
        LocationDomainModel(
            cityName = model.cityName,
            countryName = model.countryName,
        )
}
