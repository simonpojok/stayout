package com.example.stayout.presentation.mapper

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.presentation.model.FacilityCategoryUiModel

class FacilityCategoryToPresentationMapper :
    BaseDomainToPresentationMapper<FacilityCategoryDomainModel, FacilityCategoryUiModel> {
    override fun map(model: FacilityCategoryDomainModel): FacilityCategoryUiModel =
        FacilityCategoryUiModel(
            name = model.name,
            facilities = model.facilities,
        )
}
