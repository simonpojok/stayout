package com.example.stayout.data.mapper

import com.example.stayout.data.local.entity.CompanyEntity
import com.example.stayout.data.remote.model.CompanyDataModel

class CompanyDataToEntityMapper {
    fun map(model: CompanyDataModel): CompanyEntity =
        CompanyEntity(
            name = model.name,
            catchPhrase = model.catchPhrase,
            bs = model.bs,
        )
}
