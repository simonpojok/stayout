package com.example.stayout.presentation.list

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.EmptyState
import com.example.stayout.presentation.components.ErrorState
import com.example.stayout.presentation.components.GeneralAppBar
import com.example.stayout.presentation.components.LoadingState
import com.example.stayout.presentation.components.OfflineBanner
import com.example.stayout.presentation.components.PropertyCard
import com.example.stayout.presentation.components.loadingMoreItems
import com.example.stayout.presentation.preview.PreviewData.previewLocation
import com.example.stayout.presentation.preview.PreviewData.previewProperty
import com.example.stayout.presentation.preview.PreviewData.previewPropertyNoFeature
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun PropertyListScreen(
    onEvent: (PropertyListEvent) -> Unit,
    viewModel: PropertyListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnEvent by rememberUpdatedState(onEvent)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event -> currentOnEvent(event) }
    }

    PropertyListContent(
        state = state,
        onIntent = viewModel::onIntent,
        scrollIndex = viewModel.scrollIndex,
        onSaveScroll = viewModel::saveScrollPosition,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PropertyListContent(
    state: PropertyListState,
    onIntent: (PropertyListIntent) -> Unit,
    scrollIndex: Int = 0,
    onSaveScroll: (Int) -> Unit = {},
) {
    val isDark = isSystemInDarkTheme()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val focusManager = LocalFocusManager.current
    val currentOnIntent by rememberUpdatedState(onIntent)
    val currentOnSaveScroll by rememberUpdatedState(onSaveScroll)

    val appBarTitle =
        if (state is PropertyListState.Success) {
            state.location.cityName
        } else {
            "StayScout"
        }
    val appBarSubtitle = (state as? PropertyListState.Success)?.location?.countryName

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GeneralAppBar(
                title = appBarTitle,
                subtitle = appBarSubtitle,
                actions = {
                    IconButton(onClick = { onIntent(PropertyListIntent.ToggleTheme) }) {
                        Icon(
                            imageVector = if (isDark) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                            contentDescription =
                                if (isDark) {
                                    stringResource(R.string.cd_switch_to_light_mode)
                                } else {
                                    stringResource(R.string.cd_switch_to_dark_mode)
                                },
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            OfflineBanner(visible = (state as? PropertyListState.Success)?.isOffline == true)
            Box(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is PropertyListState.Loading -> LoadingState()

                    is PropertyListState.Error ->
                        ErrorState(
                            message = state.message,
                            onRetry = { onIntent(PropertyListIntent.Load) },
                        )

                    is PropertyListState.Success -> {
                        val listState =
                            rememberLazyListState(
                                initialFirstVisibleItemIndex = scrollIndex,
                            )

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
                            if (nearEnd) currentOnIntent(PropertyListIntent.LoadMore)
                        }

                        val pullRefreshState = rememberPullToRefreshState()

                        PullToRefreshBox(
                            isRefreshing = state.isRefreshing,
                            onRefresh = { onIntent(PropertyListIntent.Refresh) },
                            state = pullRefreshState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                OutlinedTextField(
                                    value = state.searchQuery,
                                    onValueChange = { onIntent(PropertyListIntent.UpdateSearch(it)) },
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8),
                                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                                    leadingIcon = {
                                        Icon(Icons.Outlined.Search, contentDescription = null)
                                    },
                                    trailingIcon = {
                                        if (state.searchQuery.isNotEmpty()) {
                                            IconButton(onClick = {
                                                onIntent(PropertyListIntent.UpdateSearch(""))
                                                focusManager.clearFocus()
                                            }) {
                                                Icon(
                                                    Icons.Outlined.Close,
                                                    contentDescription = stringResource(R.string.cd_clear_search),
                                                )
                                            }
                                        }
                                    },
                                    singleLine = true,
                                    shape = MaterialTheme.shapes.large,
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                    keyboardActions =
                                        KeyboardActions(
                                            onSearch = { focusManager.clearFocus() },
                                        ),
                                )

                                if (state.displayedProperties.isEmpty()) {
                                    if (state.searchQuery.isNotBlank()) {
                                        EmptyState(
                                            modifier = Modifier.weight(1f),
                                            title = stringResource(R.string.empty_search_title),
                                            message =
                                                stringResource(
                                                    R.string.empty_search_message,
                                                    state.searchQuery,
                                                ),
                                        )
                                    } else {
                                        EmptyState(
                                            modifier = Modifier.weight(1f),
                                            title = stringResource(R.string.empty_properties_title),
                                            message = stringResource(R.string.empty_properties_message),
                                        )
                                    }
                                } else {
                                    LazyColumn(
                                        state = listState,
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        items(
                                            items = state.displayedProperties,
                                            key = { it.id },
                                        ) { property ->
                                            PropertyCard(
                                                property = property,
                                                onClick = { onIntent(PropertyListIntent.SelectProperty(property)) },
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
            } // Box
        } // Column
    }
}

// region Previews

@PreviewThemes
@Composable
private fun PropertyListLoadingPreview() {
    StayScoutTheme {
        PropertyListContent(
            state = PropertyListState.Loading,
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyListErrorPreview() {
    StayScoutTheme {
        PropertyListContent(
            state = PropertyListState.Error("No internet connection. Please try again."),
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyListSuccessPreview() {
    StayScoutTheme {
        PropertyListContent(
            state =
                PropertyListState.Success(
                    location = previewLocation,
                    allProperties = listOf(previewProperty, previewPropertyNoFeature),
                    pageEnd = 6,
                ),
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyListLoadingMorePreview() {
    StayScoutTheme {
        PropertyListContent(
            state =
                PropertyListState.Success(
                    location = previewLocation,
                    allProperties = listOf(previewProperty, previewPropertyNoFeature),
                    pageEnd = 2,
                    isLoadingMore = true,
                ),
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyListSearchPreview() {
    StayScoutTheme {
        PropertyListContent(
            state =
                PropertyListState.Success(
                    location = previewLocation,
                    allProperties = listOf(previewProperty, previewPropertyNoFeature),
                    pageEnd = 6,
                    searchQuery = "Kinlay",
                ),
            onIntent = {},
        )
    }
}

// endregion
