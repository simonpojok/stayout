package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
internal fun PropertyRoomTypePricing(
    dormPriceValue: BigDecimal?,
    privatePriceValue: BigDecimal?,
    modifier: Modifier = Modifier,
) {
    if (dormPriceValue == null && privatePriceValue == null) return
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing16),
    ) {
        dormPriceValue?.let {
            Text(
                text =
                    stringResource(
                        R.string.label_dorm_price_format,
                        "€${it.setScale(2, RoundingMode.HALF_UP).toPlainString()}",
                    ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        privatePriceValue?.let {
            Text(
                text =
                    stringResource(
                        R.string.label_private_price_format,
                        "€${it.setScale(2, RoundingMode.HALF_UP).toPlainString()}",
                    ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@PreviewThemes
@Composable
private fun PropertyRoomTypePricingPreview() {
    StayScoutTheme {
        PropertyRoomTypePricing(
            dormPriceValue = BigDecimal("14.18"),
            privatePriceValue = BigDecimal("55.76"),
        )
    }
}
