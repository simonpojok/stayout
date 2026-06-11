package com.example.stayout.data.mapper

import com.example.stayout.data.local.entity.AddressEntity
import com.example.stayout.data.remote.model.AddressDataModel

class AddressDataToEntityMapper {
    fun map(model: AddressDataModel): AddressEntity =
        AddressEntity(
            street = model.street,
            suite = model.suite,
            city = model.city,
            zipcode = model.zipcode,
            geoLat = model.geo.lat,
            geoLng = model.geo.lng,
        )
}
