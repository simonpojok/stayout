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
                is HomeIntent.ToggleTheme -> {
                    if (currentState !is HomeState.Ready) return
                    persist(!intent.currentEffectiveIsDark)
                }
                is HomeIntent.UpdateSearch ->
                    updateState {
                        (this as? HomeState.Ready)?.copy(searchQuery = intent.query) ?: this
                    }
                HomeIntent.TapAvatar,
                HomeIntent.TapNotifications,
                -> emitEvent(HomeEvent.ShowComingSoon)
            }
        }

        private fun observeTheme() {
            viewModelScope.launch {
                observeThemeUseCase()
                    .onEach { stored ->
                        updateState {
                            when (this) {
                                HomeState.Initializing -> HomeState.Ready(isDarkTheme = stored)
                                is HomeState.Ready -> copy(isDarkTheme = stored)
                            }
                        }
                        emitEvent(HomeEvent.ThemeChanged(isDarkTheme = stored))
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
