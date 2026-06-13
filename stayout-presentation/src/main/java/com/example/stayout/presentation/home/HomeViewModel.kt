package com.example.stayout.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.ObserveThemeUseCase
import com.example.stayout.domain.usecase.SetThemeUseCase
import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val observeThemeUseCase: ObserveThemeUseCase,
        private val setThemeUseCase: SetThemeUseCase,
        private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
    ) : BaseViewModel<HomeState, HomeIntent, HomeEvent>(
            initialState = HomeState.Initializing,
        ) {
        private var wasOffline = false

        init {
            observeTheme()
            observeNetworkStatus()
        }

        override fun onIntent(intent: HomeIntent) {
            when (intent) {
                HomeIntent.ToggleTheme -> {
                    val current = currentState as? HomeState.Ready ?: return
                    persist(!current.isDarkTheme)
                }
                is HomeIntent.UpdateSearch ->
                    updateState {
                        (this as? HomeState.Ready)?.copy(searchQuery = intent.query) ?: this
                    }
            }
        }

        private fun observeTheme() {
            viewModelScope.launch {
                observeThemeUseCase()
                    .onEach { stored ->
                        val isDark = stored ?: false
                        updateState {
                            when (this) {
                                HomeState.Initializing -> HomeState.Ready(isDarkTheme = isDark)
                                is HomeState.Ready -> copy(isDarkTheme = isDark)
                            }
                        }
                        emitEvent(HomeEvent.ThemeChanged(isDarkTheme = isDark))
                    }.launchIn(this)
            }
        }

        private fun observeNetworkStatus() {
            viewModelScope.launch {
                observeNetworkStatusUseCase()
                    .onEach { isOnline ->
                        if (isOnline && wasOffline) {
                            emitEvent(HomeEvent.BackOnline)
                        }
                        wasOffline = !isOnline
                        updateState {
                            (this as? HomeState.Ready)?.copy(isOffline = !isOnline) ?: this
                        }
                    }.launchIn(this)
            }
        }

        private fun persist(isDark: Boolean) {
            viewModelScope.launch { setThemeUseCase(isDark) }
        }
    }
