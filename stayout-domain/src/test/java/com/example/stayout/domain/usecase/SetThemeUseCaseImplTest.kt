package com.example.stayout.domain.usecase

import com.example.stayout.domain.repository.ThemeRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetThemeUseCaseImplTest {
    private val repository = mockk<ThemeRepository>()
    private val useCase = SetThemeUseCaseImpl(repository)

    @Test
    fun `execute true calls repository setDarkTheme with true`() =
        runTest {
            coJustRun { repository.setDarkTheme(true) }

            useCase(true)

            coVerify { repository.setDarkTheme(true) }
        }

    @Test
    fun `execute false calls repository setDarkTheme with false`() =
        runTest {
            coJustRun { repository.setDarkTheme(false) }

            useCase(false)

            coVerify { repository.setDarkTheme(false) }
        }
}
