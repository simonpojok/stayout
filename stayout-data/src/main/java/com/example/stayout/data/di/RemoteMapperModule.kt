package com.example.stayout.data.di

import com.example.stayout.data.mapper.ExchangeRatesToDomainMapper
import com.example.stayout.data.mapper.FacilityCategoryToDomainMapper
import com.example.stayout.data.mapper.FacilityToDomainMapper
import com.example.stayout.data.mapper.LocationToDomainMapper
import com.example.stayout.data.mapper.PromotionToDomainMapper
import com.example.stayout.data.mapper.PropertiesResponseToDomainMapper
import com.example.stayout.data.mapper.PropertyToDomainMapper
import com.example.stayout.data.mapper.RatingBreakdownToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteMapperModule {
    @Provides
    @Singleton
    fun provideFacilityToDomainMapper(): FacilityToDomainMapper = FacilityToDomainMapper()

    @Provides
    @Singleton
    fun provideFacilityCategoryToDomainMapper(facilityMapper: FacilityToDomainMapper): FacilityCategoryToDomainMapper =
        FacilityCategoryToDomainMapper(facilityMapper)

    @Provides
    @Singleton
    fun provideLocationToDomainMapper(): LocationToDomainMapper = LocationToDomainMapper()

    @Provides
    @Singleton
    fun provideRatingBreakdownToDomainMapper(): RatingBreakdownToDomainMapper = RatingBreakdownToDomainMapper()

    @Provides
    @Singleton
    fun providePromotionToDomainMapper(): PromotionToDomainMapper = PromotionToDomainMapper()

    @Provides
    @Singleton
    fun providePropertyToDomainMapper(
        facilityCategoryMapper: FacilityCategoryToDomainMapper,
        ratingBreakdownMapper: RatingBreakdownToDomainMapper,
        promotionMapper: PromotionToDomainMapper,
    ): PropertyToDomainMapper = PropertyToDomainMapper(facilityCategoryMapper, ratingBreakdownMapper, promotionMapper)

    @Provides
    @Singleton
    fun providePropertiesResponseToDomainMapper(
        locationMapper: LocationToDomainMapper,
        propertyMapper: PropertyToDomainMapper,
    ): PropertiesResponseToDomainMapper = PropertiesResponseToDomainMapper(locationMapper, propertyMapper)

    @Provides
    @Singleton
    fun provideExchangeRatesToDomainMapper(): ExchangeRatesToDomainMapper = ExchangeRatesToDomainMapper()
}
