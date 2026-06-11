package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.UserEntity
import com.example.stayout.data.mapper.BaseDataToDomainMapper
import com.example.stayout.domain.model.UserDomainModel

class UserEntityToDomainMapper(
    private val addressMapper: AddressEntityToDomainMapper,
    private val companyMapper: CompanyEntityToDomainMapper,
) : BaseDataToDomainMapper<UserEntity, UserDomainModel> {
    override fun map(model: UserEntity): UserDomainModel =
        UserDomainModel(
            id = model.id,
            name = model.name,
            username = model.username,
            email = model.email,
            address = addressMapper.map(model.address),
            phone = model.phone,
            website = model.website,
            company = companyMapper.map(model.company),
        )
}
