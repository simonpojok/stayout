package com.example.stayout.domain.di

import com.example.stayout.domain.repository.NetworkStatusRepository
import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.RatesRepository
import com.example.stayout.domain.repository.StatsRepository
import com.example.stayout.domain.usecase.GetExchangeRatesUseCase
import com.example.stayout.domain.usecase.GetExchangeRatesUseCaseImpl
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.GetPropertiesUseCaseImpl
import com.example.stayout.domain.usecase.GetPropertyByIdUseCase
import com.example.stayout.domain.usecase.GetPropertyByIdUseCaseImpl
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetPropertiesUseCase(repository: PropertyRepository): GetPropertiesUseCase =
        GetPropertiesUseCaseImpl(repository)

    @Provides
    fun provideGetExchangeRatesUseCase(repository: RatesRepository): GetExchangeRatesUseCase =
        GetExchangeRatesUseCaseImpl(repository)

    @Provides
    fun provideGetPropertyByIdUseCase(
        repository: PropertyRepository,
        statsRepository: StatsRepository,
    ): GetPropertyByIdUseCase = GetPropertyByIdUseCaseImpl(repository, statsRepository)

    @Provides
    fun provideObserveNetworkStatusUseCase(repository: NetworkStatusRepository): ObserveNetworkStatusUseCase =
        ObserveNetworkStatusUseCaseImpl(repository)
}
