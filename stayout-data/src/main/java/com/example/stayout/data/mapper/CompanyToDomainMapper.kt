package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.CompanyDataModel
import com.example.stayout.domain.model.CompanyDomainModel

class CompanyToDomainMapper : BaseDataToDomainMapper<CompanyDataModel, CompanyDomainModel> {
    override fun map(model: CompanyDataModel): CompanyDomainModel =
        CompanyDomainModel(
            name = model.name,
            catchPhrase = model.catchPhrase,
            bs = model.bs,
        )
}
