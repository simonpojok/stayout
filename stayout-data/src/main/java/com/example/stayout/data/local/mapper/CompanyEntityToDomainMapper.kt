package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.CompanyEntity
import com.example.stayout.data.mapper.BaseDataToDomainMapper
import com.example.stayout.domain.model.CompanyDomainModel

class CompanyEntityToDomainMapper : BaseDataToDomainMapper<CompanyEntity, CompanyDomainModel> {
    override fun map(model: CompanyEntity): CompanyDomainModel =
        CompanyDomainModel(
            name = model.name,
            catchPhrase = model.catchPhrase,
            bs = model.bs,
        )
}
