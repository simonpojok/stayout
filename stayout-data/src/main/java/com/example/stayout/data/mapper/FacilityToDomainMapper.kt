package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.FacilityDataModel
import com.example.stayout.domain.model.FacilityDomainModel

class FacilityToDomainMapper : BaseDataToDomainMapper<FacilityDataModel, FacilityDomainModel> {
    override fun map(model: FacilityDataModel): FacilityDomainModel =
        FacilityDomainModel(
            id = model.id,
            name = model.name,
        )
}
