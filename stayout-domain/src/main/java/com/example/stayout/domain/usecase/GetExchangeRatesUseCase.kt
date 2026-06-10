package com.example.stayout.domain.usecase

import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.repository.RatesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

abstract class GetExchangeRatesUseCase(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseNoParamUseCase<Result<ExchangeRatesDomainModel>>(dispatcher)

class GetExchangeRatesUseCaseImpl
    @Inject
    constructor(
        private val repository: RatesRepository,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : GetExchangeRatesUseCase(dispatcher) {
        override suspend fun execute(): Result<ExchangeRatesDomainModel> = repository.getExchangeRates()
    }
