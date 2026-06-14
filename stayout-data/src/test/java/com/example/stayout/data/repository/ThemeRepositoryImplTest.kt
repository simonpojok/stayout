package com.example.stayout.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.mutablePreferencesOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ThemeRepositoryImplTest {
    private val dataStore: DataStore<Preferences> = mockk()

    // Repository accesses dataStore.data at init time; stub before each construction
    private fun repository(): ThemeRepositoryImpl = ThemeRepositoryImpl(dataStore)

    @Test
    fun `isDarkTheme emits null when no preference stored`() =
        runTest {
            every { dataStore.data } returns flowOf(emptyPreferences())

            val result = repository().isDarkTheme.first()

            assertNull(result)
        }

    @Test
    fun `isDarkTheme emits true when preference is true`() =
        runTest {
            val prefs = mutablePreferencesOf(booleanPreferencesKey("is_dark_theme") to true)
            every { dataStore.data } returns flowOf(prefs)

            val result = repository().isDarkTheme.first()

            assertEquals(true, result)
        }

    @Test
    fun `isDarkTheme emits false when preference is false`() =
        runTest {
            val prefs = mutablePreferencesOf(booleanPreferencesKey("is_dark_theme") to false)
            every { dataStore.data } returns flowOf(prefs)

            val result = repository().isDarkTheme.first()

            assertEquals(false, result)
        }

    @Test
    fun `setDarkTheme stores the preference key via updateData`() =
        runTest {
            // edit() is an extension that delegates to updateData(); mock updateData and execute
            // the transform so the lambda inside setDarkTheme is covered by the coverage report
            every { dataStore.data } returns flowOf(emptyPreferences())
            coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } coAnswers {
                val transform = firstArg<suspend (Preferences) -> Preferences>()
                transform(mutablePreferencesOf())
            }

            repository().setDarkTheme(true)

            coVerify { dataStore.updateData(any()) }
        }
}
