package com.example.stayout.presentation.di

import android.content.Context
import com.example.stayout.presentation.mapper.FacilityCategoryToPresentationMapper
import com.example.stayout.presentation.mapper.LocationToPresentationMapper
import com.example.stayout.presentation.mapper.PropertyToPresentationMapper
import com.example.stayout.presentation.provider.AndroidResourceProvider
import com.example.stayout.presentation.provider.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {
    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context,
    ): ResourceProvider = AndroidResourceProvider(context)

    @Provides
    @Singleton
    fun provideFacilityCategoryToPresentationMapper() = FacilityCategoryToPresentationMapper()

    @Provides
    @Singleton
    fun provideLocationToPresentationMapper() = LocationToPresentationMapper()

    @Provides
    @Singleton
    fun providePropertyToPresentationMapper(
        resources: ResourceProvider,
        facilityMapper: FacilityCategoryToPresentationMapper,
    ) = PropertyToPresentationMapper(resources, facilityMapper)
}
