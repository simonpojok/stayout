package com.example.stayout.presentation.detail.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.PropertyMapView
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun PropertyLocationSection(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED,
        )
    }
    var rationaleVisible by rememberSaveable { mutableStateOf(false) }
    val shouldShowRationale =
        (context as? Activity)
            ?.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) == true

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            rationaleVisible = false
        }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            if (shouldShowRationale) {
                rationaleVisible = true
            } else {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                )
            }
        }
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.label_location_map),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        if (rationaleVisible && !hasLocationPermission) {
            LocationPermissionRationaleCard(
                onGrant = {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                        ),
                    )
                },
                onDismiss = { rationaleVisible = false },
            )
            Spacer(modifier = Modifier.height(Dimens.spacing8))
        }
        PropertyMapView(
            latitude = latitude,
            longitude = longitude,
            hasLocationPermission = hasLocationPermission,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing200),
        )
    }
}

@Composable
private fun LocationPermissionRationaleCard(
    onGrant: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(Dimens.spacing16)) {
            Text(
                text = stringResource(R.string.rationale_location_title),
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing4))
            Text(
                text = stringResource(R.string.rationale_location_body),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.btn_dismiss))
                }
                Spacer(modifier = Modifier.width(Dimens.spacing8))
                OutlinedButton(onClick = onGrant) {
                    Text(stringResource(R.string.btn_grant_location))
                }
            }
        }
    }
}

// region Previews

@PreviewThemes
@Composable
private fun PropertyLocationSectionPreview() {
    StayScoutTheme {
        PropertyLocationSection(
            latitude = 53.3437259,
            longitude = -6.269898,
        )
    }
}

// endregion
