package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun PromotionChip(promotion: PromotionDomainModel) {
    SuggestionChip(
        onClick = {},
        label = {
            Text(
                text = "${promotion.discount}% ${promotion.type.name}",
                style = MaterialTheme.typography.labelSmall,
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.LocalOffer,
                contentDescription = null,
                modifier = Modifier.size(SuggestionChipDefaults.IconSize),
            )
        },
        colors =
            SuggestionChipDefaults.suggestionChipColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                iconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
    )
}

@PreviewThemes
@Composable
private fun PromotionChipPreview() {
    StayScoutTheme {
        PromotionChip(
            promotion = PromotionDomainModel(type = PromotionType.MOBILE, label = "Mobile Deal", discount = 10),
        )
    }
}
