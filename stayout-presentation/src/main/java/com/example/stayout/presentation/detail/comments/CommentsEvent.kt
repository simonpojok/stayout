package com.example.stayout.presentation.detail.comments

import com.example.stayout.presentation.base.BaseEvent

sealed interface CommentsEvent : BaseEvent {
    data object None : CommentsEvent
}
