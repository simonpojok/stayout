package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.RatingBreakdownDataModel
import com.example.stayout.domain.model.RatingBreakdownDomainModel

class RatingBreakdownToDomainMapper : BaseDataToDomainMapper<RatingBreakdownDataModel, RatingBreakdownDomainModel> {
    override fun map(model: RatingBreakdownDataModel) =
        RatingBreakdownDomainModel(
            security = model.security / 10.0,
            location = model.location / 10.0,
            staff = model.staff / 10.0,
            funScore = model.funScore / 10.0,
            cleanliness = model.cleanliness / 10.0,
            facilities = model.facilities / 10.0,
            value = model.value / 10.0,
            ratingsCount = model.ratingsCount,
        )
}
