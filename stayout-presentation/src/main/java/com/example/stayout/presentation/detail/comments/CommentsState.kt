package com.example.stayout.presentation.detail.comments

import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.presentation.base.BaseState

sealed interface CommentsState : BaseState {
    data object Loading : CommentsState

    data class Success(
        val comments: List<CommentDomainModel>,
    ) : CommentsState

    data class Error(
        val message: String,
    ) : CommentsState
}
