package com.example.stayout.presentation.home

import com.example.stayout.presentation.base.BaseEvent

sealed interface HomeEvent : BaseEvent {
    data class ThemeChanged(
        val isDarkTheme: Boolean?,
    ) : HomeEvent

    data object BackOnline : HomeEvent

    data object ShowComingSoon : HomeEvent
}
