package com.example.stayscout.di

import com.example.stayout.data.di.BaseUrl
import com.example.stayout.data.di.IsDebug
import com.example.stayscout.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @IsDebug
    fun provideIsDebug(): Boolean = BuildConfig.DEBUG
}
