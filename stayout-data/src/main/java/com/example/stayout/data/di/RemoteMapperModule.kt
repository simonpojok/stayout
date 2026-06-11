package com.example.stayout.data.di

import com.example.stayout.data.mapper.AddressToDomainMapper
import com.example.stayout.data.mapper.CommentToDomainMapper
import com.example.stayout.data.mapper.CompanyToDomainMapper
import com.example.stayout.data.mapper.ExchangeRatesToDomainMapper
import com.example.stayout.data.mapper.FacilityCategoryToDomainMapper
import com.example.stayout.data.mapper.LocationToDomainMapper
import com.example.stayout.data.mapper.PropertiesResponseToDomainMapper
import com.example.stayout.data.mapper.PropertyToDomainMapper
import com.example.stayout.data.mapper.UserToDomainMapper
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
    fun provideFacilityCategoryToDomainMapper(): FacilityCategoryToDomainMapper = FacilityCategoryToDomainMapper()

    @Provides
    @Singleton
    fun provideLocationToDomainMapper(): LocationToDomainMapper = LocationToDomainMapper()

    @Provides
    @Singleton
    fun providePropertyToDomainMapper(facilityCategoryMapper: FacilityCategoryToDomainMapper): PropertyToDomainMapper =
        PropertyToDomainMapper(facilityCategoryMapper)

    @Provides
    @Singleton
    fun providePropertiesResponseToDomainMapper(
        locationMapper: LocationToDomainMapper,
        propertyMapper: PropertyToDomainMapper,
    ): PropertiesResponseToDomainMapper = PropertiesResponseToDomainMapper(locationMapper, propertyMapper)

    @Provides
    @Singleton
    fun provideExchangeRatesToDomainMapper(): ExchangeRatesToDomainMapper = ExchangeRatesToDomainMapper()

    @Provides
    @Singleton
    fun provideAddressToDomainMapper(): AddressToDomainMapper = AddressToDomainMapper()

    @Provides
    @Singleton
    fun provideCompanyToDomainMapper(): CompanyToDomainMapper = CompanyToDomainMapper()

    @Provides
    @Singleton
    fun provideUserToDomainMapper(
        addressMapper: AddressToDomainMapper,
        companyMapper: CompanyToDomainMapper,
    ): UserToDomainMapper = UserToDomainMapper(addressMapper, companyMapper)

    @Provides
    @Singleton
    fun provideCommentToDomainMapper(userMapper: UserToDomainMapper): CommentToDomainMapper =
        CommentToDomainMapper(userMapper)
}
