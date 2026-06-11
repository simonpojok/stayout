package com.example.stayout.data.di

import com.example.stayout.data.local.mapper.AddressEntityToDomainMapper
import com.example.stayout.data.local.mapper.CommentEntityToDomainMapper
import com.example.stayout.data.local.mapper.CompanyEntityToDomainMapper
import com.example.stayout.data.local.mapper.UserEntityToDomainMapper
import com.example.stayout.data.mapper.AddressDataToEntityMapper
import com.example.stayout.data.mapper.CompanyDataToEntityMapper
import com.example.stayout.data.mapper.UserDataToEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserEntityMapperModule {
    @Provides
    @Singleton
    fun provideAddressEntityToDomainMapper(): AddressEntityToDomainMapper = AddressEntityToDomainMapper()

    @Provides
    @Singleton
    fun provideCompanyEntityToDomainMapper(): CompanyEntityToDomainMapper = CompanyEntityToDomainMapper()

    @Provides
    @Singleton
    fun provideUserEntityToDomainMapper(
        addressMapper: AddressEntityToDomainMapper,
        companyMapper: CompanyEntityToDomainMapper,
    ): UserEntityToDomainMapper = UserEntityToDomainMapper(addressMapper, companyMapper)

    @Provides
    @Singleton
    fun provideCommentEntityToDomainMapper(userEntityToDomain: UserEntityToDomainMapper): CommentEntityToDomainMapper =
        CommentEntityToDomainMapper(userEntityToDomain)

    @Provides
    @Singleton
    fun provideAddressDataToEntityMapper(): AddressDataToEntityMapper = AddressDataToEntityMapper()

    @Provides
    @Singleton
    fun provideCompanyDataToEntityMapper(): CompanyDataToEntityMapper = CompanyDataToEntityMapper()

    @Provides
    @Singleton
    fun provideUserDataToEntityMapper(
        addressMapper: AddressDataToEntityMapper,
        companyMapper: CompanyDataToEntityMapper,
    ): UserDataToEntityMapper = UserDataToEntityMapper(addressMapper, companyMapper)
}
