package com.example.stayout.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel
    @Inject
    constructor(
        private val getPropertiesUseCase: GetPropertiesUseCase,
        private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
        private val savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<PropertyListState, PropertyListIntent, PropertyListEvent>(
            initialState = PropertyListState.Loading,
        ) {
        val scrollIndex: Int
            get() = savedStateHandle.get<Int>(KEY_SCROLL_INDEX) ?: 0

        init {
            onIntent(PropertyListIntent.Load)
            observeNetworkStatus()
        }

        override fun onIntent(intent: PropertyListIntent) {
            when (intent) {
                PropertyListIntent.Load -> loadProperties()
                PropertyListIntent.Refresh -> refreshProperties()
                PropertyListIntent.LoadMore -> {
                    val current = currentState as? PropertyListState.Success ?: return
                    if (current.canLoadMore && !current.isLoadingMore) loadMore()
                }
                is PropertyListIntent.SelectProperty ->
                    emitEvent(PropertyListEvent.NavigateToDetail(intent.property.id))
                is PropertyListIntent.UpdateSearch ->
                    updateState {
                        (this as? PropertyListState.Success)?.copy(searchQuery = intent.query) ?: this
                    }
                PropertyListIntent.ToggleTheme -> emitEvent(PropertyListEvent.ToggleTheme)
            }
        }

        fun saveScrollPosition(index: Int) {
            savedStateHandle[KEY_SCROLL_INDEX] = index
        }

        private fun observeNetworkStatus() {
            viewModelScope.launch {
                observeNetworkStatusUseCase()
                    .onEach { isOnline ->
                        updateState {
                            (this as? PropertyListState.Success)?.copy(isOffline = !isOnline) ?: this
                        }
                    }.launchIn(this)
            }
        }

        private fun loadProperties() {
            viewModelScope.launch {
                updateState { PropertyListState.Loading }
                getPropertiesUseCase()
                    .onSuccess { (location, properties) ->
                        updateState {
                            PropertyListState.Success(
                                location = location,
                                allProperties = properties,
                                pageEnd = PAGE_SIZE,
                            )
                        }
                    }.onFailure { e ->
                        updateState { PropertyListState.Error(e.message ?: "Failed to load properties") }
                    }
            }
        }

        private fun refreshProperties() {
            updateState {
                (this as? PropertyListState.Success)?.copy(isRefreshing = true) ?: this
            }
            viewModelScope.launch {
                getPropertiesUseCase()
                    .onSuccess { (location, properties) ->
                        updateState {
                            PropertyListState.Success(
                                location = location,
                                allProperties = properties,
                                pageEnd = PAGE_SIZE,
                            )
                        }
                    }.onFailure {
                        updateState {
                            (this as? PropertyListState.Success)?.copy(isRefreshing = false) ?: this
                        }
                    }
            }
        }

        private fun loadMore() {
            updateState {
                (this as? PropertyListState.Success)?.copy(isLoadingMore = true) ?: this
            }
            viewModelScope.launch {
                delay(LOAD_MORE_DELAY_MS)
                updateState {
                    (this as? PropertyListState.Success)?.let { s ->
                        s.copy(
                            pageEnd = minOf(s.pageEnd + PAGE_SIZE, s.allProperties.size),
                            isLoadingMore = false,
                        )
                    } ?: this
                }
            }
        }

        companion object {
            private const val KEY_SCROLL_INDEX = "scroll_index"
            private const val PAGE_SIZE = 6
            private const val LOAD_MORE_DELAY_MS = 600L
        }
    }
