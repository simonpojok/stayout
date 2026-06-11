package com.example.stayout.presentation.detail.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.stayout.domain.usecase.GetCommentsUseCase
import com.example.stayout.presentation.base.BaseViewModel
import com.example.stayout.presentation.detail.PropertyDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.await
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel
    @Inject
    constructor(
        private val getCommentsUseCase: GetCommentsUseCase,
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<CommentsState, CommentsIntent, CommentsEvent>(
            initialState = CommentsState.Loading,
        ) {
        private val postId: Int =
            checkNotNull(savedStateHandle[PropertyDetailViewModel.ARG_PROPERTY_ID]) {
                "propertyId nav argument is missing"
            }

        init {
            loadComments()
        }

        override fun onIntent(intent: CommentsIntent) {
            when (intent) {
                CommentsIntent.Retry -> loadComments()
            }
        }

        private fun loadComments() {
            viewModelScope.launch {
                updateState { CommentsState.Loading }
                runCatching { getCommentsUseCase(postId).await() }
                    .onSuccess { comments -> updateState { CommentsState.Success(comments) } }
                    .onFailure { error ->
                        updateState { CommentsState.Error(error.message ?: "Failed to load reviews") }
                    }
            }
        }
    }
