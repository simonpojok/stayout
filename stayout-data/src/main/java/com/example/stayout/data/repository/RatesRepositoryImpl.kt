package com.example.stayout.data.repository

import com.example.stayout.data.local.dao.ExchangeRatesDao
import com.example.stayout.data.local.mapper.ExchangeRatesDomainToEntityMapper
import com.example.stayout.data.local.mapper.ExchangeRatesEntityToDomainMapper
import com.example.stayout.data.mapper.ExchangeRatesToDomainMapper
import com.example.stayout.data.remote.api.RatesApi
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.repository.RatesRepository
import com.example.stayout.domain.repository.StatsEvent
import com.example.stayout.domain.repository.StatsRepository

class RatesRepositoryImpl(
    private val api: RatesApi,
    private val mapper: ExchangeRatesToDomainMapper,
    private val statsRepository: StatsRepository,
    private val ratesDao: ExchangeRatesDao,
    private val ratesEntityToDomain: ExchangeRatesEntityToDomainMapper,
    private val ratesDomainToEntity: ExchangeRatesDomainToEntityMapper,
) : RatesRepository {
    override suspend fun getExchangeRates(): Result<ExchangeRatesDomainModel> {
        val start = System.currentTimeMillis()
        return try {
            val response = api.getRates()
            statsRepository.trackEvent(StatsEvent.LOAD_RATES, System.currentTimeMillis() - start)
            val result = mapper.map(response)
            ratesDao.insert(ratesDomainToEntity.map(result))
            Result.success(result)
        } catch (e: Exception) {
            ratesDao
                .get()
                ?.let { Result.success(ratesEntityToDomain.map(it)) }
                ?: Result.failure(e)
        }
    }
}
