package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.FacilityDetailBottomSheet
import com.example.stayout.presentation.preview.PreviewData
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import kotlinx.coroutines.delay

@Composable
internal fun PropertyFacilitiesSection(
    facilities: List<FacilityCategoryDomainModel>,
    modifier: Modifier = Modifier,
) {
    val allFacilities = remember(facilities) { facilities.flatMap { it.facilities } }
    var selectedFacility by remember { mutableStateOf<FacilityDomainModel?>(null) }
    val listState = rememberLazyListState()

    LaunchedEffect(allFacilities.size) {
        if (allFacilities.size > 1) {
            delay(1000L)
            while (true) {
                delay(2500L)
                val next = listState.firstVisibleItemIndex + 1
                if (next < allFacilities.size) {
                    listState.animateScrollToItem(next)
                } else {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.label_facilities),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { heading() },
        )
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        ) {
            items(allFacilities) { facility ->
                FacilityChip(
                    facility = facility,
                    onClick = { selectedFacility = facility },
                )
            }
        }
    }

    selectedFacility?.let { facility ->
        FacilityDetailBottomSheet(
            facility = facility,
            onDismiss = { selectedFacility = null },
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyFacilitiesSectionPreview() {
    StayScoutTheme {
        PropertyFacilitiesSection(facilities = PreviewData.previewProperty.facilities)
    }
}
