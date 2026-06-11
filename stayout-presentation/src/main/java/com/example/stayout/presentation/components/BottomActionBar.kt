package com.example.stayout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.button.PrimaryButton
import com.example.stayout.presentation.detail.PropertyDetailIntent
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun BottomActionBar(
    onIntent: (PropertyDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
    applyNavigationBarPadding: Boolean = false,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .then(if (applyNavigationBarPadding) Modifier.navigationBarsPadding() else Modifier),
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing72)
                    .padding(horizontal = Dimens.spacing16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        ) {
            val iconButtonShape = RoundedCornerShape(4.dp)
            val iconButtonColors =
                IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            FilledIconButton(
                onClick = { onIntent(PropertyDetailIntent.Share) },
                shape = iconButtonShape,
                colors = iconButtonColors,
                modifier = Modifier.size(Dimens.spacing44),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = stringResource(R.string.cd_share),
                )
            }
            FilledIconButton(
                onClick = { onIntent(PropertyDetailIntent.Favorite) },
                shape = iconButtonShape,
                colors = iconButtonColors,
                modifier = Modifier.size(Dimens.spacing44),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = stringResource(R.string.cd_favorite),
                )
            }
            FilledIconButton(
                onClick = { onIntent(PropertyDetailIntent.Location) },
                shape = iconButtonShape,
                colors = iconButtonColors,
                modifier = Modifier.size(Dimens.spacing44),
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = stringResource(R.string.cd_location),
                )
            }
            PrimaryButton(
                label = stringResource(R.string.btn_book_now),
                onClick = { onIntent(PropertyDetailIntent.Book) },
                modifier =
                    Modifier
                        .weight(1f)
                        .height(Dimens.spacing44),
            )
        }
    }
}

// region Previews

@PreviewThemes
@Composable
private fun BottomActionBarPreview() {
    StayScoutTheme {
        BottomActionBar(onIntent = {})
    }
}

@PreviewThemes
@Composable
private fun BottomActionBarWithNavPaddingPreview() {
    StayScoutTheme {
        BottomActionBar(onIntent = {}, applyNavigationBarPadding = true)
    }
}

// endregion
