package com.example.stayout.presentation.list

import com.example.stayout.presentation.base.BaseEvent

sealed interface PropertyListEvent : BaseEvent {
    data class NavigateToDetail(
        val propertyId: Int,
    ) : PropertyListEvent

    data object ToggleTheme : PropertyListEvent
}
