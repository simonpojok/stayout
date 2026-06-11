package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import com.example.stayout.presentation.util.facilityDescriptionRes
import com.example.stayout.presentation.util.facilityIconFor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacilityDetailBottomSheet(
    facilityName: String,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val icon = facilityIconFor(facilityName)
    val description = stringResource(facilityDescriptionRes(facilityName))

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.spacing24)
                    .padding(bottom = Dimens.spacing32),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(Dimens.spacing72),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier =
                        Modifier
                            .padding(Dimens.spacing16)
                            .fillMaxSize(),
                )
            }
            Spacer(modifier = Modifier.height(Dimens.spacing16))
            Text(
                text = facilityName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Dimens.spacing16))
        }
    }
}

@PreviewThemes
@Composable
private fun FacilityDetailBottomSheetWifiPreview() {
    StayScoutTheme {
        FacilityDetailBottomSheet(facilityName = "Free WiFi", onDismiss = {})
    }
}

@PreviewThemes
@Composable
private fun FacilityDetailBottomSheetBarPreview() {
    StayScoutTheme {
        FacilityDetailBottomSheet(facilityName = "Bar", onDismiss = {})
    }
}
