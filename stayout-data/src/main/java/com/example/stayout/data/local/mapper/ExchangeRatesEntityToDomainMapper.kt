package com.example.stayout.data.local.mapper

import com.example.stayout.data.local.entity.ExchangeRatesEntity
import com.example.stayout.data.mapper.BaseDataToDomainMapper
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import java.math.BigDecimal

class ExchangeRatesEntityToDomainMapper : BaseDataToDomainMapper<ExchangeRatesEntity, ExchangeRatesDomainModel> {
    override fun map(model: ExchangeRatesEntity): ExchangeRatesDomainModel =
        ExchangeRatesDomainModel(
            usd = BigDecimal(model.usd),
            gbp = BigDecimal(model.gbp),
        )
}
