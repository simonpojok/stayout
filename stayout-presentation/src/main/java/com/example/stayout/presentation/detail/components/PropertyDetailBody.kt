package com.example.stayout.presentation.detail.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.example.stayout.domain.model.priceIn
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.CurrencySelector
import com.example.stayout.presentation.components.FacilityDetailBottomSheet
import com.example.stayout.presentation.components.FeaturedBadge
import com.example.stayout.presentation.components.PropertyMapView
import com.example.stayout.presentation.components.RatingBadge
import com.example.stayout.presentation.detail.PropertyDetailIntent
import com.example.stayout.presentation.detail.PropertyDetailState
import com.example.stayout.presentation.theme.Dimens
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PropertyDetailBody(
    state: PropertyDetailState.Success,
    onIntent: (PropertyDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val property = state.property
    val context = LocalContext.current

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED,
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            hasLocationPermission =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        }
    }

    val allFacilities =
        remember(property.facilities) {
            property.facilities.flatMap { it.facilities }
        }
    var selectedFacility by remember { mutableStateOf<String?>(null) }
    val facilityListState = rememberLazyListState()

    LaunchedEffect(allFacilities.size) {
        if (allFacilities.size > 1) {
            delay(1000L)
            while (true) {
                delay(2500L)
                val next = facilityListState.firstVisibleItemIndex + 1
                if (next < allFacilities.size) {
                    facilityListState.animateScrollToItem(next)
                } else {
                    facilityListState.animateScrollToItem(0)
                }
            }
        }
    }

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

            if (allFacilities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                Text(text = stringResource(R.string.label_facilities), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                LazyRow(
                    state = facilityListState,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                    contentPadding = PaddingValues(),
                ) {
                    items(allFacilities) { facility ->
                        FacilityChip(
                            name = facility,
                            onClick = { selectedFacility = facility },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.spacing16))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.spacing16))

            Text(
                text = stringResource(R.string.label_location_map),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            PropertyMapView(
                latitude = state.mapLocation.latitude,
                longitude = state.mapLocation.longitude,
                hasLocationPermission = hasLocationPermission,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(Dimens.spacing200),
            )

            Spacer(modifier = Modifier.height(Dimens.spacing32))
        }
    }

    if (selectedFacility != null) {
        FacilityDetailBottomSheet(
            facilityName = selectedFacility!!,
            onDismiss = { selectedFacility = null },
        )
    }
}
