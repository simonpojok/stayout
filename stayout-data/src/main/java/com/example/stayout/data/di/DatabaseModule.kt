package com.example.stayout.data.di

import android.content.Context
import androidx.room.Room
import com.example.stayout.data.local.StayScoutDatabase
import com.example.stayout.data.local.migration.Migration1To2
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
            .addMigrations(Migration1To2())
            .build()
    }
}
