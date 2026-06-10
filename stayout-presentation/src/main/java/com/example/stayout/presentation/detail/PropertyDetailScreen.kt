package com.example.stayout.presentation.detail

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.stayout.domain.model.priceIn
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.CurrencySelector
import com.example.stayout.presentation.components.ErrorState
import com.example.stayout.presentation.components.FeaturedBadge
import com.example.stayout.presentation.components.GeneralAppBar
import com.example.stayout.presentation.components.OfflineBanner
import com.example.stayout.presentation.components.RatingBadge
import com.example.stayout.presentation.preview.PreviewData.previewProperty
import com.example.stayout.presentation.preview.PreviewData.previewRates
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

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

    PropertyDetailContent(
        state = state,
        onBack = { onEvent(PropertyDetailEvent.NavigateBack) },
        onIntent = viewModel::onIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PropertyDetailContent(
    state: PropertyDetailState,
    onBack: () -> Unit,
    onIntent: (PropertyDetailIntent) -> Unit,
) {
    val propertyName = (state as? PropertyDetailState.Success)?.property?.name ?: ""

    Scaffold(
        topBar = {
            GeneralAppBar(
                title = propertyName,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OfflineBanner(visible = (state as? PropertyDetailState.Success)?.isOffline == true)
            when (state) {
                is PropertyDetailState.Loading -> {
                    PropertyDetailSkeleton()
                }

                is PropertyDetailState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = { onIntent(PropertyDetailIntent.Retry) },
                    )
                }

                is PropertyDetailState.Success -> {
                    PropertyDetailBody(
                        state = state,
                        onIntent = onIntent,
                    )
                }
            }
        }
    }
}

@Composable
private fun PropertyDetailSkeleton(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "detailShimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 900),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "detailShimmerAlpha",
    )
    val shimmer = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
    val pill = MaterialTheme.shapes.extraSmall
    val rounded = MaterialTheme.shapes.medium

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing260)
                    .background(shimmer),
        )
        Column(modifier = Modifier.padding(Dimens.spacing16)) {
            Box(modifier = Modifier.fillMaxWidth(0.75f).height(Dimens.spacing30).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8)) {
                Box(modifier = Modifier.width(Dimens.spacing48).height(Dimens.spacing26).background(shimmer, pill))
                Box(modifier = Modifier.width(Dimens.spacing80).height(Dimens.spacing26).background(shimmer, pill))
                Box(modifier = Modifier.width(Dimens.spacing64).height(Dimens.spacing26).background(shimmer, pill))
            }
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Box(modifier = Modifier.fillMaxWidth(0.55f).height(Dimens.spacing16).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing20))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Box(modifier = Modifier.width(Dimens.spacing130).height(Dimens.spacing20).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Box(modifier = Modifier.fillMaxWidth().height(Dimens.spacing44).background(shimmer, rounded))
            Spacer(modifier = Modifier.height(Dimens.spacing14))
            Box(modifier = Modifier.width(Dimens.spacing120).height(Dimens.spacing48).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing6))
            Box(modifier = Modifier.width(Dimens.spacing64).height(Dimens.spacing14).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing20))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Box(modifier = Modifier.width(Dimens.spacing56).height(Dimens.spacing20).background(shimmer, pill))
            Spacer(modifier = Modifier.height(Dimens.spacing10))
            repeat(4) {
                Box(modifier = Modifier.fillMaxWidth().height(Dimens.spacing14).background(shimmer, pill))
                Spacer(modifier = Modifier.height(Dimens.spacing6))
            }
            Spacer(modifier = Modifier.height(Dimens.spacing32))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PropertyDetailBody(
    state: PropertyDetailState.Success,
    onIntent: (PropertyDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val property = state.property

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = property.thumbnailUrl,
            contentDescription = stringResource(R.string.cd_hero_image_format, property.name),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing260),
            contentScale = ContentScale.Crop,
        )

        Column(modifier = Modifier.padding(Dimens.spacing16)) {
            Text(
                text = property.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RatingBadge(rating = property.rating)
                if (property.isFeatured) FeaturedBadge()
                SuggestionChip(onClick = {}, label = { Text(property.type) })
            }
            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimens.spacing18),
                )
                Spacer(modifier = Modifier.width(Dimens.spacing4))
                Text(
                    text = property.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Spacer(modifier = Modifier.height(Dimens.spacing16))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))

            Text(text = stringResource(R.string.label_price_per_night), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(Dimens.spacing10))
            CurrencySelector(
                selectedCurrency = state.selectedCurrency,
                onCurrencySelect = { onIntent(PropertyDetailIntent.SelectCurrency(it)) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Text(
                text = property.priceIn(state.selectedCurrency, state.rates),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(R.string.label_per_night),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (state.ratesUnavailable) {
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                Text(
                    text = stringResource(R.string.label_rates_unavailable),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            if (property.freeCancellationAvailable) {
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(Dimens.spacing12),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                        )
                        Spacer(modifier = Modifier.width(Dimens.spacing8))
                        Text(
                            text = stringResource(R.string.label_free_cancellation),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.spacing16))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))

            Text(text = stringResource(R.string.label_about), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = HtmlCompat.fromHtml(property.overview, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (property.facilities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                Text(text = stringResource(R.string.label_facilities), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                property.facilities.forEach { category ->
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(Dimens.spacing4))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing4),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        category.facilities.forEach { facility ->
                            AssistChip(
                                onClick = {},
                                label = { Text(text = facility, style = MaterialTheme.typography.labelSmall) },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Dimens.spacing8))
                }
            }

            Spacer(modifier = Modifier.height(Dimens.spacing32))
        }
    }
}

// region Previews

@PreviewThemes
@Composable
private fun PropertyDetailSkeletonPreview() {
    StayScoutTheme {
        PropertyDetailContent(
            state = PropertyDetailState.Loading,
            onBack = {},
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyDetailErrorPreview() {
    StayScoutTheme {
        PropertyDetailContent(
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
        PropertyDetailContent(
            state =
                PropertyDetailState.Success(
                    property = previewProperty,
                    rates = previewRates,
                ),
            onBack = {},
            onIntent = {},
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyDetailRatesUnavailablePreview() {
    StayScoutTheme {
        PropertyDetailContent(
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
