package com.example.stayout.data.mapper

import com.example.stayout.data.remote.model.RatesResponseDataModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import java.math.BigDecimal

class ExchangeRatesToDomainMapper : BaseDataToDomainMapper<RatesResponseDataModel, ExchangeRatesDomainModel> {
    override fun map(model: RatesResponseDataModel): ExchangeRatesDomainModel =
        ExchangeRatesDomainModel(
            usd = model.rates["USD"] ?: BigDecimal.ONE,
            gbp = model.rates["GBP"] ?: BigDecimal.ONE,
        )
}
