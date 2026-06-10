package com.example.stayout.data.di

import android.content.Context
import com.example.stayout.data.security.DatabasePassphraseRepository
import com.example.stayout.data.security.DatabasePassphraseRepositoryImpl
import com.example.stayout.data.security.KeystorePassphraseCipher
import com.example.stayout.data.security.SharedPreferencesPassphraseStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {
    @Provides
    @Singleton
    fun provideDatabasePassphraseRepository(
        @ApplicationContext context: Context,
    ): DatabasePassphraseRepository =
        DatabasePassphraseRepositoryImpl(
            store = SharedPreferencesPassphraseStore(context),
            cipher = KeystorePassphraseCipher(),
        )
}
