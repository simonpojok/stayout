package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.AddressDataModel
import com.example.stayout.domain.model.AddressDomainModel

class AddressToDomainMapper : BaseDataToDomainMapper<AddressDataModel, AddressDomainModel> {
    override fun map(model: AddressDataModel): AddressDomainModel =
        AddressDomainModel(
            street = model.street,
            suite = model.suite,
            city = model.city,
            zipcode = model.zipcode,
            latitude = model.geo.lat.toDouble(),
            longitude = model.geo.lng.toDouble(),
        )
}
