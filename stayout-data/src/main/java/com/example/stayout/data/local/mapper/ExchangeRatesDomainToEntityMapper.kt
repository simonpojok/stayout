package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.ExchangeRatesEntity
import com.example.stayout.data.mapper.BaseDomainToDataMapper
import com.example.stayout.domain.model.ExchangeRatesDomainModel

class ExchangeRatesDomainToEntityMapper : BaseDomainToDataMapper<ExchangeRatesDomainModel, ExchangeRatesEntity> {
    override fun map(model: ExchangeRatesDomainModel): ExchangeRatesEntity =
        ExchangeRatesEntity(
            usd = model.usd.toPlainString(),
            gbp = model.gbp.toPlainString(),
        )
}
