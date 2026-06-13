package com.example.stayout.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stayout.presentation.detail.components.PropertyDetailScaffold

@Composable
fun PropertyDetailScreen(
    onEvent: (PropertyDetailEvent) -> Unit,
    viewModel: PropertyDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnEvent by rememberUpdatedState(onEvent)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event -> currentOnEvent(event) }
    }

    PropertyDetailScaffold(
        state = state,
        onBack = { onEvent(PropertyDetailEvent.NavigateBack) },
        onIntent = viewModel::onIntent,
    )
}
