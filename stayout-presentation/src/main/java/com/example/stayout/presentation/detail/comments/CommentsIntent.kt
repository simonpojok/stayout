package com.example.stayout.presentation.detail.comments

import com.example.stayout.presentation.base.BaseIntent

sealed interface CommentsIntent : BaseIntent {
    data object Retry : CommentsIntent
}
