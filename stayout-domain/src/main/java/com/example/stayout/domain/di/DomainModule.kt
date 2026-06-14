package com.example.stayout.domain.di

import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.RatesRepository
import com.example.stayout.domain.usecase.GetCommentsUseCase
import com.example.stayout.domain.usecase.GetCommentsUseCaseImpl
import com.example.stayout.domain.usecase.GetExchangeRatesUseCase
import com.example.stayout.domain.usecase.GetExchangeRatesUseCaseImpl
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.GetPropertiesUseCaseImpl
import com.example.stayout.domain.usecase.GetPropertyByIdUseCase
import com.example.stayout.domain.usecase.GetPropertyByIdUseCaseImpl
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCaseImpl
import com.example.stayout.domain.usecase.ObserveThemeUseCase
import com.example.stayout.domain.usecase.ObserveThemeUseCaseImpl
import com.example.stayout.domain.usecase.SetThemeUseCase
import com.example.stayout.domain.usecase.SetThemeUseCaseImpl
import com.example.stayout.domain.usecase.TrackEventUseCase
import com.example.stayout.domain.usecase.TrackEventUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindObserveNetworkStatusUseCase(impl: ObserveNetworkStatusUseCaseImpl): ObserveNetworkStatusUseCase

    @Binds
    abstract fun bindObserveThemeUseCase(impl: ObserveThemeUseCaseImpl): ObserveThemeUseCase

    @Binds
    abstract fun bindSetThemeUseCase(impl: SetThemeUseCaseImpl): SetThemeUseCase

    @Binds
    abstract fun bindTrackEventUseCase(impl: TrackEventUseCaseImpl): TrackEventUseCase

    @Binds
    abstract fun bindGetCommentsUseCase(impl: GetCommentsUseCaseImpl): GetCommentsUseCase

    companion object {
        @Provides
        fun provideGetPropertiesUseCase(repository: PropertyRepository): GetPropertiesUseCase =
            GetPropertiesUseCaseImpl(repository)

        @Provides
        fun provideGetExchangeRatesUseCase(repository: RatesRepository): GetExchangeRatesUseCase =
            GetExchangeRatesUseCaseImpl(repository)

        @Provides
        fun provideGetPropertyByIdUseCase(repository: PropertyRepository): GetPropertyByIdUseCase =
            GetPropertyByIdUseCaseImpl(repository)
    }
}
