package com.example.stayout.domain.model

import java.math.BigDecimal

data class ExchangeRatesDomainModel(
    val eur: BigDecimal = BigDecimal.ONE,
    val usd: BigDecimal,
    val gbp: BigDecimal,
)
