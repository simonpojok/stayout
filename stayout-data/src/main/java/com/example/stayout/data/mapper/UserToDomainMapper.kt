package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.UserDataModel
import com.example.stayout.domain.model.UserDomainModel

class UserToDomainMapper(
    private val addressMapper: AddressToDomainMapper,
    private val companyMapper: CompanyToDomainMapper,
) : BaseDataToDomainMapper<UserDataModel, UserDomainModel> {
    override fun map(model: UserDataModel): UserDomainModel =
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
