package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityCategoryDataModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel

class FacilityCategoryToDomainMapper :
    BaseDataToDomainMapper<FacilityCategoryDataModel, FacilityCategoryDomainModel> {
    override fun map(model: FacilityCategoryDataModel): FacilityCategoryDomainModel =
        FacilityCategoryDomainModel(
            name = model.name,
            facilities = model.facilities.map { it.name },
        )
}
