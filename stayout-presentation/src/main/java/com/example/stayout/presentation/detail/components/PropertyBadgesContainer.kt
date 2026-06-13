package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun PropertyBadgesContainer(
    type: String,
    isNew: Boolean,
    veryPopular: Boolean,
    isRecommended: Boolean,
    modifier: Modifier = Modifier,
) {
    val badgeNew = stringResource(R.string.badge_new)
    val badgePopular = stringResource(R.string.badge_popular)
    val labelHwRecommends = stringResource(R.string.label_hw_recommends)

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing4),
    ) {
        SuggestionChip(
            onClick = {},
            modifier = Modifier.clearAndSetSemantics { contentDescription = type },
            label = { Text(type) },
        )
        if (isNew) {
            SuggestionChip(
                onClick = {},
                modifier = Modifier.clearAndSetSemantics { contentDescription = badgeNew },
                label = { Text(badgeNew) },
                colors =
                    SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
            )
        }
        if (veryPopular) {
            SuggestionChip(
                onClick = {},
                modifier = Modifier.clearAndSetSemantics { contentDescription = badgePopular },
                label = { Text(badgePopular) },
                colors =
                    SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
            )
        }
        if (isRecommended) {
            SuggestionChip(
                onClick = {},
                modifier = Modifier.clearAndSetSemantics { contentDescription = labelHwRecommends },
                label = { Text(labelHwRecommends) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Stars,
                        contentDescription = null,
                        modifier = Modifier.size(SuggestionChipDefaults.IconSize),
                    )
                },
                colors =
                    SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        iconContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
            )
        }
    }
}

@PreviewThemes
@Composable
private fun PropertyBadgesContainerPreview() {
    StayScoutTheme {
        PropertyBadgesContainer(
            type = "Hostel",
            isNew = true,
            veryPopular = true,
            isRecommended = true,
        )
    }
}
