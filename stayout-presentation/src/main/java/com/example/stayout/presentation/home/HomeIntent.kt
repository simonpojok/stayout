package com.example.stayout.presentation.home

import com.example.stayout.presentation.base.BaseIntent

sealed interface HomeIntent : BaseIntent {
    data class ToggleTheme(
        val currentEffectiveIsDark: Boolean,
    ) : HomeIntent

    data class UpdateSearch(
        val query: String,
    ) : HomeIntent

    data object TapAvatar : HomeIntent

    data object TapNotifications : HomeIntent
}
