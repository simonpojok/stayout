package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.AddressEntity
import com.example.stayout.data.mapper.BaseDataToDomainMapper
import com.example.stayout.domain.model.AddressDomainModel

class AddressEntityToDomainMapper : BaseDataToDomainMapper<AddressEntity, AddressDomainModel> {
    override fun map(model: AddressEntity): AddressDomainModel =
        AddressDomainModel(
            street = model.street,
            suite = model.suite,
            city = model.city,
            zipcode = model.zipcode,
            latitude = model.geoLat.toDouble(),
            longitude = model.geoLng.toDouble(),
        )
}
