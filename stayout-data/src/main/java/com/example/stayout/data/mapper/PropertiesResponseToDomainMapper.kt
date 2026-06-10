package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.PropertiesResponseDataModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel

class PropertiesResponseToDomainMapper(
    private val locationMapper: LocationToDomainMapper,
    private val propertyMapper: PropertyToDomainMapper,
) : BaseDataToDomainMapper<PropertiesResponseDataModel, Pair<LocationDomainModel, List<PropertyDomainModel>>> {
    override fun map(model: PropertiesResponseDataModel): Pair<LocationDomainModel, List<PropertyDomainModel>> =
        locationMapper.map(model.location) to model.properties.map { propertyMapper.map(it) }
}
