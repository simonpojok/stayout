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
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.EmptyState
import com.example.stayout.presentation.components.ErrorState
import com.example.stayout.presentation.components.LoadingState
import com.example.stayout.presentation.components.PropertyCard
import com.example.stayout.presentation.components.loadingMoreItems
import com.example.stayout.presentation.preview.PreviewData.previewLocation
import com.example.stayout.presentation.preview.PreviewData.previewProperty
import com.example.stayout.presentation.preview.PreviewData.previewPropertyNoFeature
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExploreSection(
    state: ExploreState,
    onIntent: (ExploreIntent) -> Unit,
    scrollIndex: Int = 0,
    onSaveScroll: (Int) -> Unit = {},
) {
    val currentOnIntent by rememberUpdatedState(onIntent)
    val currentOnSaveScroll by rememberUpdatedState(onSaveScroll)

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is ExploreState.Loading -> LoadingState()

            is ExploreState.Error ->
                ErrorState(
                    message = state.message,
                    onRetry = { onIntent(ExploreIntent.Load) },
                )

            is ExploreState.Success -> {
                val listState = rememberLazyListState(initialFirstVisibleItemIndex = scrollIndex)

                DisposableEffect(listState) {
                    onDispose { currentOnSaveScroll(listState.firstVisibleItemIndex) }
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
                    if (nearEnd) currentOnIntent(ExploreIntent.LoadMore)
                }

                val pullRefreshState = rememberPullToRefreshState()

                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { onIntent(ExploreIntent.Refresh) },
                    state = pullRefreshState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (state.displayedProperties.isEmpty()) {
                        if (state.searchQuery.isNotBlank()) {
                            EmptyState(
                                modifier = Modifier.fillMaxSize(),
                                title = stringResource(R.string.empty_search_title),
                                message =
                                    stringResource(
                                        R.string.empty_search_message,
                                        state.searchQuery,
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
                                items = state.displayedProperties,
                                key = { it.id },
                            ) { property ->
                                PropertyCard(
                                    property = property,
                                    onClick = { onIntent(ExploreIntent.SelectProperty(property)) },
                                )
                            }
                            if (state.isLoadingMore) {
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
        ExploreSection(state = ExploreState.Loading, onIntent = {})
    }
}

@PreviewThemes
@Composable
private fun ExploreSectionErrorPreview() {
    StayScoutTheme {
        ExploreSection(
            state = ExploreState.Error("No internet connection."),
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun ExploreSectionSuccessPreview() {
    StayScoutTheme {
        ExploreSection(
            state =
                ExploreState.Success(
                    location = previewLocation,
                    allProperties = listOf(previewProperty, previewPropertyNoFeature),
                    pageEnd = 6,
                ),
            onIntent = {},
        )
    }
}

// endregion
