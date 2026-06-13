package com.example.stayout.domain.di

import com.example.stayout.domain.repository.AnalyticsRepository
import com.example.stayout.domain.repository.CommentRepository
import com.example.stayout.domain.repository.NetworkStatusRepository
import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.RatesRepository
import com.example.stayout.domain.repository.StatsRepository
import com.example.stayout.domain.repository.ThemeRepository
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

    @Provides
    fun provideGetCommentsUseCase(repository: CommentRepository): GetCommentsUseCase =
        GetCommentsUseCaseImpl(repository)

    @Provides
    fun provideObserveThemeUseCase(repository: ThemeRepository): ObserveThemeUseCase =
        ObserveThemeUseCaseImpl(repository)

    @Provides
    fun provideSetThemeUseCase(repository: ThemeRepository): SetThemeUseCase = SetThemeUseCaseImpl(repository)

    @Provides
    fun provideTrackEventUseCase(repository: AnalyticsRepository): TrackEventUseCase = TrackEventUseCaseImpl(repository)
}
