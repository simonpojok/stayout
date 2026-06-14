package com.example.stayout.data.di

import com.example.stayout.data.local.StayScoutDatabase
import com.example.stayout.data.local.dao.CommentDao
import com.example.stayout.data.local.dao.ExchangeRatesDao
import com.example.stayout.data.local.dao.LocationDao
import com.example.stayout.data.local.dao.PropertyDao
import com.example.stayout.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providePropertyDao(db: StayScoutDatabase): PropertyDao = db.propertyDao()

    @Provides
    fun provideLocationDao(db: StayScoutDatabase): LocationDao = db.locationDao()

    @Provides
    fun provideExchangeRatesDao(db: StayScoutDatabase): ExchangeRatesDao = db.exchangeRatesDao()

    @Provides
    fun provideCommentDao(db: StayScoutDatabase): CommentDao = db.commentDao()

    @Provides
    fun provideUserDao(db: StayScoutDatabase): UserDao = db.userDao()
}
