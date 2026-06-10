package com.example.stayout.data.di

import android.content.Context
import androidx.room.Room
import com.example.stayout.data.local.StayScoutDatabase
import com.example.stayout.data.local.dao.ExchangeRatesDao
import com.example.stayout.data.local.dao.LocationDao
import com.example.stayout.data.local.dao.PropertyDao
import com.example.stayout.data.security.DatabasePassphraseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        passphraseRepository: DatabasePassphraseRepository,
    ): StayScoutDatabase {
        val passphrase = passphraseRepository.getOrCreatePassphrase()
        val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase.map { it.toInt().toChar() }.toCharArray()))
        passphrase.fill(0)
        return Room
            .databaseBuilder(context, StayScoutDatabase::class.java, "stayscout.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun providePropertyDao(db: StayScoutDatabase): PropertyDao = db.propertyDao()

    @Provides
    fun provideLocationDao(db: StayScoutDatabase): LocationDao = db.locationDao()

    @Provides
    fun provideExchangeRatesDao(db: StayScoutDatabase): ExchangeRatesDao = db.exchangeRatesDao()
}
