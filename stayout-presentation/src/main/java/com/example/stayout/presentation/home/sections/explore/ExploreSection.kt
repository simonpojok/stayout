package com.example.stayout.presentation.home.sections.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.EmptyState
import com.example.stayout.presentation.components.ErrorState
import com.example.stayout.presentation.components.LoadingState
import com.example.stayout.presentation.components.PropertyCard
import com.example.stayout.presentation.components.loadingMoreItems
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExploreSection(
    searchQuery: String,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnNavigateToDetail by rememberUpdatedState(onNavigateToDetail)

    LaunchedEffect(searchQuery) {
        viewModel.onIntent(ExploreIntent.UpdateSearch(searchQuery))
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is ExploreEvent.NavigateToDetail -> currentOnNavigateToDetail(event.propertyId)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val currentState = state) {
            is ExploreState.Loading -> LoadingState()

            is ExploreState.Error ->
                ErrorState(
                    message = currentState.message,
                    onRetry = { viewModel.onIntent(ExploreIntent.Load) },
                )

            is ExploreState.Success -> {
                val listState =
                    rememberLazyListState(
                        initialFirstVisibleItemIndex = viewModel.scrollIndex,
                    )

                DisposableEffect(listState) {
                    onDispose { viewModel.saveScrollPosition(listState.firstVisibleItemIndex) }
                }

                val nearEnd by remember {
                    derivedStateOf {
                        val lastVisible =
                            listState.layoutInfo.visibleItemsInfo
                                .lastOrNull()
                                ?.index ?: 0
                        val total = listState.layoutInfo.totalItemsCount
                        total > 0 && lastVisible >= total - 3
                    }
                }

                LaunchedEffect(nearEnd) {
                    if (nearEnd) viewModel.onIntent(ExploreIntent.LoadMore)
                }

                val pullRefreshState = rememberPullToRefreshState()

                PullToRefreshBox(
                    isRefreshing = currentState.isRefreshing,
                    onRefresh = { viewModel.onIntent(ExploreIntent.Refresh) },
                    state = pullRefreshState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (currentState.displayedProperties.isEmpty()) {
                        if (currentState.searchQuery.isNotBlank()) {
                            EmptyState(
                                modifier = Modifier.fillMaxSize(),
                                title = stringResource(R.string.empty_search_title),
                                message =
                                    stringResource(
                                        R.string.empty_search_message,
                                        currentState.searchQuery,
                                    ),
                            )
                        } else {
                            EmptyState(
                                modifier = Modifier.fillMaxSize(),
                                title = stringResource(R.string.empty_properties_title),
                                message = stringResource(R.string.empty_properties_message),
                            )
                        }
                    } else {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(
                                items = currentState.displayedProperties,
                                key = { it.id },
                            ) { property ->
                                PropertyCard(
                                    property = property,
                                    onClick = {
                                        viewModel.onIntent(ExploreIntent.SelectProperty(property))
                                    },
                                )
                            }
                            if (currentState.isLoadingMore) {
                                loadingMoreItems(count = 2)
                            }
                        }
                    }
                }
            }
        }
    }
}

// region Previews

@PreviewThemes
@Composable
private fun ExploreSectionLoadingPreview() {
    StayScoutTheme {
        ExploreSection(searchQuery = "", onNavigateToDetail = {})
    }
}

@PreviewThemes
@Composable
private fun ExploreSectionErrorPreview() {
    StayScoutTheme {
        ExploreSection(searchQuery = "", onNavigateToDetail = {})
    }
}

@PreviewThemes
@Composable
private fun ExploreSectionSuccessPreview() {
    StayScoutTheme {
        ExploreSection(searchQuery = "", onNavigateToDetail = {})
    }
}

// endregion
