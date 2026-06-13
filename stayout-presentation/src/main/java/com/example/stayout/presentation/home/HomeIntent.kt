package com.example.stayout.presentation.home

import com.example.stayout.presentation.base.BaseIntent

sealed interface HomeIntent : BaseIntent {
    data object ToggleTheme : HomeIntent

    data class UpdateSearch(
        val query: String,
    ) : HomeIntent
}
