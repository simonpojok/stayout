package com.example.stayout.presentation.home

import com.example.stayout.presentation.base.BaseState

sealed interface HomeState : BaseState {
    data object Initializing : HomeState

    data class Ready(
        val isDarkTheme: Boolean,
        val searchQuery: String = "",
        val isOffline: Boolean = false,
    ) : HomeState
}
