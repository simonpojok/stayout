package com.example.stayout.data.di

import com.example.stayout.data.local.mapper.ExchangeRatesDomainToEntityMapper
import com.example.stayout.data.local.mapper.ExchangeRatesEntityToDomainMapper
import com.example.stayout.data.local.mapper.LocationDomainToEntityMapper
import com.example.stayout.data.local.mapper.LocationEntityToDomainMapper
import com.example.stayout.data.local.mapper.PropertyDomainToEntityMapper
import com.example.stayout.data.local.mapper.PropertyEntityToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PropertyEntityMapperModule {
    @Provides
    @Singleton
    fun provideLocationEntityToDomainMapper(): LocationEntityToDomainMapper = LocationEntityToDomainMapper()

    @Provides
    @Singleton
    fun provideLocationDomainToEntityMapper(): LocationDomainToEntityMapper = LocationDomainToEntityMapper()

    @Provides
    @Singleton
    fun providePropertyEntityToDomainMapper(): PropertyEntityToDomainMapper = PropertyEntityToDomainMapper()

    @Provides
    @Singleton
    fun providePropertyDomainToEntityMapper(): PropertyDomainToEntityMapper = PropertyDomainToEntityMapper()

    @Provides
    @Singleton
    fun provideExchangeRatesEntityToDomainMapper(): ExchangeRatesEntityToDomainMapper =
        ExchangeRatesEntityToDomainMapper()

    @Provides
    @Singleton
    fun provideExchangeRatesDomainToEntityMapper(): ExchangeRatesDomainToEntityMapper =
        ExchangeRatesDomainToEntityMapper()
}
