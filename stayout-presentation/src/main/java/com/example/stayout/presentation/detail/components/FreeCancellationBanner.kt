package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
internal fun FreeCancellationBanner(
    freeCancellationUntil: String?,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
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
            Column {
                Text(
                    text = stringResource(R.string.label_free_cancellation),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                freeCancellationUntil?.let { date ->
                    Text(
                        text = stringResource(R.string.label_free_cancellation_until, date),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                }
            }
        }
    }
}

@PreviewThemes
@Composable
private fun FreeCancellationBannerWithDatePreview() {
    StayScoutTheme {
        FreeCancellationBanner(freeCancellationUntil = "2021-11-16")
    }
}

@PreviewThemes
@Composable
private fun FreeCancellationBannerNoDatePreview() {
    StayScoutTheme {
        FreeCancellationBanner(freeCancellationUntil = null)
    }
}
