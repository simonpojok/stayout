package com.example.stayout.presentation.detail

import com.example.stayout.presentation.base.BaseEvent

sealed interface PropertyDetailEvent : BaseEvent {
    data object NavigateBack : PropertyDetailEvent
}
