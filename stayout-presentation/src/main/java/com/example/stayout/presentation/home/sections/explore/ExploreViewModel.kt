package com.example.stayout.presentation.home.sections.explore

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.stayout.domain.model.AnalyticsEvent
import com.example.stayout.domain.usecase.GetPropertiesUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.domain.usecase.TrackEventUseCase
import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel
    @Inject
    constructor(
        private val getPropertiesUseCase: GetPropertiesUseCase,
        private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
        private val savedStateHandle: SavedStateHandle,
        private val trackEventUseCase: TrackEventUseCase,
    ) : BaseViewModel<ExploreState, ExploreIntent, ExploreEvent>(
            initialState = ExploreState.Loading,
        ) {
        val scrollIndex: Int
            get() = savedStateHandle.get<Int>(KEY_SCROLL_INDEX) ?: 0

        init {
            onIntent(ExploreIntent.Load)
            observeNetworkStatus()
            track(AnalyticsEvent.ScreenViewed("explore"))
        }

        override fun onIntent(intent: ExploreIntent) {
            when (intent) {
                ExploreIntent.Load -> loadProperties()
                ExploreIntent.Refresh -> refreshProperties()
                ExploreIntent.LoadMore -> {
                    val current = currentState as? ExploreState.Success ?: return
                    if (current.canLoadMore && !current.isLoadingMore) loadMore()
                }
                is ExploreIntent.SelectProperty -> {
                    track(AnalyticsEvent.PropertyTapped(intent.property.id))
                    emitEvent(ExploreEvent.NavigateToDetail(intent.property.id))
                }
                is ExploreIntent.UpdateSearch -> {
                    updateState {
                        (this as? ExploreState.Success)?.copy(searchQuery = intent.query) ?: this
                    }
                    val success = currentState as? ExploreState.Success ?: return
                    if (intent.query.isNotBlank()) {
                        track(AnalyticsEvent.SearchPerformed(intent.query, success.displayedProperties.size))
                    }
                }
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
                            (this as? ExploreState.Success)?.copy(isOffline = !isOnline) ?: this
                        }
                    }.launchIn(this)
            }
        }

        private fun loadProperties() {
            viewModelScope.launch {
                updateState { ExploreState.Loading }
                getPropertiesUseCase()
                    .onSuccess { (location, properties) ->
                        updateState {
                            ExploreState.Success(
                                location = location,
                                allProperties = properties,
                                pageEnd = PAGE_SIZE,
                            )
                        }
                    }.onFailure { e ->
                        updateState { ExploreState.Error(e.message ?: "Failed to load properties") }
                    }
            }
        }

        private fun refreshProperties() {
            updateState {
                (this as? ExploreState.Success)?.copy(isRefreshing = true) ?: this
            }
            viewModelScope.launch {
                getPropertiesUseCase()
                    .onSuccess { (location, properties) ->
                        updateState {
                            ExploreState.Success(
                                location = location,
                                allProperties = properties,
                                pageEnd = PAGE_SIZE,
                            )
                        }
                    }.onFailure {
                        updateState {
                            (this as? ExploreState.Success)?.copy(isRefreshing = false) ?: this
                        }
                    }
            }
        }

        private fun loadMore() {
            updateState {
                (this as? ExploreState.Success)?.copy(isLoadingMore = true) ?: this
            }
            viewModelScope.launch {
                delay(LOAD_MORE_DELAY_MS)
                updateState {
                    (this as? ExploreState.Success)?.let { s ->
                        s.copy(
                            pageEnd = minOf(s.pageEnd + PAGE_SIZE, s.allProperties.size),
                            isLoadingMore = false,
                        )
                    } ?: this
                }
            }
        }

        private fun track(event: AnalyticsEvent) {
            viewModelScope.launch { trackEventUseCase(event) }
        }

        companion object {
            private const val KEY_SCROLL_INDEX = "scroll_index"
            private const val PAGE_SIZE = 6
            private const val LOAD_MORE_DELAY_MS = 600L
        }
    }
