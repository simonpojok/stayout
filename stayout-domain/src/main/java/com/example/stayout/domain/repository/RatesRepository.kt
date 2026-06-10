package com.example.stayout.domain.repository

import com.example.stayout.domain.model.ExchangeRatesDomainModel

interface RatesRepository {
    suspend fun getExchangeRates(): Result<ExchangeRatesDomainModel>
}
