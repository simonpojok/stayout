package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.PromotionDataModel
import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType

class PromotionToDomainMapper : BaseDataToDomainMapper<PromotionDataModel, PromotionDomainModel> {
    override fun map(model: PromotionDataModel): PromotionDomainModel =
        PromotionDomainModel(
            type = PromotionType.fromString(model.type),
            label = model.name,
            discount = model.discount,
        )
}
