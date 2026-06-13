package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun PropertyLocationInfo(
    address: String,
    district: String?,
    distanceKm: Double?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimens.spacing18),
            )
            Spacer(modifier = Modifier.width(Dimens.spacing4))
            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        district?.let {
            Spacer(modifier = Modifier.height(Dimens.spacing4))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationCity,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimens.spacing18),
                )
                Spacer(modifier = Modifier.width(Dimens.spacing4))
                Text(
                    text = stringResource(R.string.label_neighbourhood) + ": $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        distanceKm?.let { km ->
            Spacer(modifier = Modifier.height(Dimens.spacing4))
            Text(
                text = stringResource(R.string.label_distance_from_centre, km),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun PropertyLocationInfoPreview() {
    StayScoutTheme {
        PropertyLocationInfo(
            address = "2-12 Lord Edward St, Dublin, Ireland",
            district = "Temple Bar",
            distanceKm = 1.01,
        )
    }
}
