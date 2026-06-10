package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                if (subtitle != null) {
                    Column {
                        Text(text = title, style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    Text(text = title, maxLines = 1)
                }
            },
            navigationIcon = { navigationIcon?.invoke() },
            actions = actions,
            scrollBehavior = scrollBehavior,
        )
        HorizontalDivider()
    }
}

// region Previews

@OptIn(ExperimentalMaterial3Api::class)
@PreviewThemes
@Composable
private fun GeneralAppBarFallbackPreview() {
    StayScoutTheme {
        GeneralAppBar(title = "StayScout")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewThemes
@Composable
private fun GeneralAppBarWithSubtitlePreview() {
    StayScoutTheme {
        GeneralAppBar(
            title = "Dublin",
            subtitle = "Ireland",
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.DarkMode,
                        contentDescription = stringResource(R.string.cd_switch_to_dark_mode),
                    )
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewThemes
@Composable
private fun GeneralAppBarWithBackPreview() {
    StayScoutTheme {
        GeneralAppBar(
            title = "Kinlay House Hostel",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back),
                    )
                }
            },
        )
    }
}

// endregion
