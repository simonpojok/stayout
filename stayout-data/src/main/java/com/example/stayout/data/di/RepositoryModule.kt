package com.example.stayout.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.stayout.data.local.dao.CommentDao
import com.example.stayout.data.local.dao.ExchangeRatesDao
import com.example.stayout.data.local.dao.LocationDao
import com.example.stayout.data.local.dao.PropertyDao
import com.example.stayout.data.local.dao.UserDao
import com.example.stayout.data.local.mapper.CommentEntityToDomainMapper
import com.example.stayout.data.local.mapper.ExchangeRatesDomainToEntityMapper
import com.example.stayout.data.local.mapper.ExchangeRatesEntityToDomainMapper
import com.example.stayout.data.local.mapper.LocationDomainToEntityMapper
import com.example.stayout.data.local.mapper.LocationEntityToDomainMapper
import com.example.stayout.data.local.mapper.PropertyDomainToEntityMapper
import com.example.stayout.data.local.mapper.PropertyEntityToDomainMapper
import com.example.stayout.data.mapper.CommentToDomainMapper
import com.example.stayout.data.mapper.ExchangeRatesToDomainMapper
import com.example.stayout.data.mapper.PropertiesResponseToDomainMapper
import com.example.stayout.data.mapper.UserDataToEntityMapper
import com.example.stayout.data.remote.api.CommentApi
import com.example.stayout.data.remote.api.PropertyApi
import com.example.stayout.data.remote.api.RatesApi
import com.example.stayout.data.remote.api.StatsApi
import com.example.stayout.data.repository.CommentRepositoryImpl
import com.example.stayout.data.repository.FirebaseAnalyticsRepository
import com.example.stayout.data.repository.LoggingAnalyticsRepository
import com.example.stayout.data.repository.NetworkStatusRepositoryImpl
import com.example.stayout.data.repository.PropertyRepositoryImpl
import com.example.stayout.data.repository.RatesRepositoryImpl
import com.example.stayout.data.repository.StatsRepositoryImpl
import com.example.stayout.data.repository.ThemeRepositoryImpl
import com.example.stayout.domain.repository.AnalyticsRepository
import com.example.stayout.domain.repository.CommentRepository
import com.example.stayout.domain.repository.NetworkStatusRepository
import com.example.stayout.domain.repository.PropertyRepository
import com.example.stayout.domain.repository.RatesRepository
import com.example.stayout.domain.repository.StatsRepository
import com.example.stayout.domain.repository.ThemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideStatsRepository(
        api: StatsApi,
        @ApplicationScope scope: CoroutineScope,
    ): StatsRepository = StatsRepositoryImpl(api, scope)

    @Provides
    @Singleton
    fun providePropertyRepository(
        api: PropertyApi,
        mapper: PropertiesResponseToDomainMapper,
        statsRepository: StatsRepository,
        propertyDao: PropertyDao,
        locationDao: LocationDao,
        propertyEntityToDomain: PropertyEntityToDomainMapper,
        propertyDomainToEntity: PropertyDomainToEntityMapper,
        locationEntityToDomain: LocationEntityToDomainMapper,
        locationDomainToEntity: LocationDomainToEntityMapper,
    ): PropertyRepository =
        PropertyRepositoryImpl(
            api = api,
            mapper = mapper,
            statsRepository = statsRepository,
            propertyDao = propertyDao,
            locationDao = locationDao,
            propertyEntityToDomain = propertyEntityToDomain,
            propertyDomainToEntity = propertyDomainToEntity,
            locationEntityToDomain = locationEntityToDomain,
            locationDomainToEntity = locationDomainToEntity,
        )

    @Provides
    @Singleton
    fun provideRatesRepository(
        api: RatesApi,
        mapper: ExchangeRatesToDomainMapper,
        statsRepository: StatsRepository,
        ratesDao: ExchangeRatesDao,
        ratesEntityToDomain: ExchangeRatesEntityToDomainMapper,
        ratesDomainToEntity: ExchangeRatesDomainToEntityMapper,
    ): RatesRepository =
        RatesRepositoryImpl(
            api = api,
            mapper = mapper,
            statsRepository = statsRepository,
            ratesDao = ratesDao,
            ratesEntityToDomain = ratesEntityToDomain,
            ratesDomainToEntity = ratesDomainToEntity,
        )

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        @IsDebug isDebug: Boolean,
        logging: LoggingAnalyticsRepository,
        firebase: FirebaseAnalyticsRepository,
    ): AnalyticsRepository = if (isDebug) logging else firebase

    @Provides
    @Singleton
    fun provideNetworkStatusRepository(impl: NetworkStatusRepositoryImpl): NetworkStatusRepository = impl

    @Provides
    @Singleton
    fun provideThemeDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.themeDataStore

    @Provides
    @Singleton
    fun provideThemeRepository(dataStore: DataStore<Preferences>): ThemeRepository = ThemeRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideCommentRepository(
        api: CommentApi,
        mapper: CommentToDomainMapper,
        commentDao: CommentDao,
        userDao: UserDao,
        userDataToEntity: UserDataToEntityMapper,
        commentEntityToDomain: CommentEntityToDomainMapper,
        @ApplicationScope scope: CoroutineScope,
    ): CommentRepository =
        CommentRepositoryImpl(api, mapper, commentDao, userDao, userDataToEntity, commentEntityToDomain, scope)
}
