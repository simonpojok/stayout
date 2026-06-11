package com.example.stayout.presentation.home.sections.explore

import com.example.stayout.presentation.base.BaseEvent

sealed interface ExploreEvent : BaseEvent {
    data class NavigateToDetail(
        val propertyId: Int,
    ) : ExploreEvent
}
