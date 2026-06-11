package com.example.stayout.data.mapper

import com.example.stayout.data.local.entity.UserEntity
import com.example.stayout.data.remote.model.UserDataModel

class UserDataToEntityMapper(
    private val addressMapper: AddressDataToEntityMapper,
    private val companyMapper: CompanyDataToEntityMapper,
) {
    fun map(model: UserDataModel): UserEntity =
        UserEntity(
            id = model.id,
            name = model.name,
            username = model.username,
            email = model.email,
            phone = model.phone,
            website = model.website,
            address = addressMapper.map(model.address),
            company = companyMapper.map(model.company),
        )
}
