package com.example.stayout.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.stayout.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val KEY_IS_DARK = booleanPreferencesKey("is_dark_theme")

class ThemeRepositoryImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : ThemeRepository {
        override val isDarkTheme: Flow<Boolean?> =
            dataStore.data.map { prefs -> prefs[KEY_IS_DARK] }

        override suspend fun setDarkTheme(isDark: Boolean) {
            dataStore.edit { prefs -> prefs[KEY_IS_DARK] = isDark }
        }
    }
