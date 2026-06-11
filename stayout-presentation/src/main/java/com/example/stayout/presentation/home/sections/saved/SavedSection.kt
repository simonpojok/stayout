package com.example.stayout.presentation.home.sections.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun SavedSection() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.coming_soon_saved),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// region Previews

@PreviewThemes
@Composable
private fun SavedSectionPreview() {
    StayScoutTheme {
        Surface { SavedSection() }
    }
}

// endregion
