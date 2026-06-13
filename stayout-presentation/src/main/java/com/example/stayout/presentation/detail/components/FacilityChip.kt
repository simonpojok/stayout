package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import com.example.stayout.presentation.util.facilityIconForId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FacilityChip(
    facility: FacilityDomainModel,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier =
            Modifier
                .size(Dimens.spacing80)
                .semantics { contentDescription = facility.name },
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacing12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = facilityIconForId(facility.id),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimens.spacing24),
            )
            Spacer(modifier = Modifier.height(Dimens.spacing4))
            Text(
                text = facility.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun FacilityChipWifiPreview() {
    StayScoutTheme {
        FacilityChip(facility = FacilityDomainModel(id = "FREEWIFI", name = "Free WiFi"), onClick = {})
    }
}

@PreviewThemes
@Composable
private fun FacilityChipLockerPreview() {
    StayScoutTheme {
        FacilityChip(facility = FacilityDomainModel(id = "LOCKERS", name = "Lockers"), onClick = {})
    }
}
