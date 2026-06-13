package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.domain.model.RatingBreakdownDomainModel
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens

@Composable
internal fun RatingBreakdownSection(
    breakdown: RatingBreakdownDomainModel,
    modifier: Modifier = Modifier,
) {
    val categories =
        listOf(
            stringResource(R.string.label_rating_security) to breakdown.security,
            stringResource(R.string.label_rating_location) to breakdown.location,
            stringResource(R.string.label_rating_staff) to breakdown.staff,
            stringResource(R.string.label_rating_fun) to breakdown.funScore,
            stringResource(R.string.label_rating_cleanliness) to breakdown.cleanliness,
            stringResource(R.string.label_rating_facilities) to breakdown.facilities,
            stringResource(R.string.label_rating_value) to breakdown.value,
        )

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.label_rating_breakdown),
            style = MaterialTheme.typography.titleMedium,
        )
        if (breakdown.ratingsCount > 0) {
            Text(
                text = stringResource(R.string.label_based_on_reviews, breakdown.ratingsCount),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.height(Dimens.spacing10))
        val chunked = categories.chunked(2)
        chunked.forEachIndexed { rowIndex, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
            ) {
                pair.forEach { (label, score) ->
                    RatingCategoryItem(
                        label = label,
                        score = score,
                        modifier = Modifier.weight(1f),
                    )
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            if (rowIndex < chunked.lastIndex) {
                Spacer(modifier = Modifier.height(Dimens.spacing8))
            }
        }
    }
}
