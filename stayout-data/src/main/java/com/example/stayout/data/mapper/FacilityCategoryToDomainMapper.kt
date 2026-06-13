package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityCategoryDataModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel

class FacilityCategoryToDomainMapper(
    private val facilityMapper: FacilityToDomainMapper,
) : BaseDataToDomainMapper<FacilityCategoryDataModel, FacilityCategoryDomainModel> {
    override fun map(model: FacilityCategoryDataModel): FacilityCategoryDomainModel =
        FacilityCategoryDomainModel(
            id = model.id,
            name = model.name,
            facilities = model.facilities.map { facilityMapper.map(it) },
        )
}
