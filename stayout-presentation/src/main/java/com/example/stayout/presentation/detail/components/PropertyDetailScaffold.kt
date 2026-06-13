package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.components.BasicAppBar
import com.example.stayout.presentation.components.BottomActionBar
import com.example.stayout.presentation.components.ErrorState
import com.example.stayout.presentation.components.OfflineBanner
import com.example.stayout.presentation.detail.PropertyDetailIntent
import com.example.stayout.presentation.detail.PropertyDetailState
import com.example.stayout.presentation.preview.PreviewData.previewProperty
import com.example.stayout.presentation.preview.PreviewData.previewRates
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PropertyDetailScaffold(
    state: PropertyDetailState,
    onBack: () -> Unit,
    onIntent: (PropertyDetailIntent) -> Unit,
) {
    val propertyName = (state as? PropertyDetailState.Success)?.property?.name ?: ""

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            BasicAppBar(title = propertyName, onNavigateBack = onBack)
        },
        bottomBar = {
            BottomActionBar(onIntent = onIntent, applyNavigationBarPadding = true)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OfflineBanner(visible = (state as? PropertyDetailState.Success)?.isOffline == true)
            when (state) {
                is PropertyDetailState.Loading -> PropertyDetailSkeleton()
                is PropertyDetailState.Error ->
                    ErrorState(
                        message = state.message,
                        onRetry = { onIntent(PropertyDetailIntent.Retry) },
                    )
                is PropertyDetailState.Success ->
                    PropertyDetailSections(state = state, onIntent = onIntent)
            }
        }
    }
}

// region Previews

@PreviewThemes
@Composable
private fun PropertyDetailSkeletonPreview() {
    StayScoutTheme {
        PropertyDetailScaffold(state = PropertyDetailState.Loading, onBack = {}, onIntent = {})
    }
}

@PreviewThemes
@Composable
private fun PropertyDetailErrorPreview() {
    StayScoutTheme {
        PropertyDetailScaffold(
            state = PropertyDetailState.Error("Property not found"),
            onBack = {},
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyDetailSuccessPreview() {
    StayScoutTheme {
        PropertyDetailScaffold(
            state = PropertyDetailState.Success(property = previewProperty, rates = previewRates),
            onBack = {},
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyDetailRatesUnavailablePreview() {
    StayScoutTheme {
        PropertyDetailScaffold(
            state =
                PropertyDetailState.Success(
                    property = previewProperty,
                    rates = previewRates,
                    ratesUnavailable = true,
                ),
            onBack = {},
            onIntent = {},
        )
    }
}

// endregion
