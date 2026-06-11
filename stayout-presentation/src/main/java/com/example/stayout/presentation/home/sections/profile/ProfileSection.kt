package com.example.stayout.presentation.home.sections.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.components.button.OutlinedButton
import com.example.stayout.presentation.components.button.PrimaryButton
import com.example.stayout.presentation.components.button.TextButton
import com.example.stayout.presentation.components.button.TonalButton
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun ProfileSection() {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.spacing16),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
    ) {
        item { ShowcaseSectionHeader(text = "Primary Button") }
        item { PrimaryButton(label = "Book Now", onClick = {}, modifier = Modifier.fillMaxWidth()) }
        item { PrimaryButton(label = "Book Now", onClick = {}, modifier = Modifier.fillMaxWidth(), isEnabled = false) }
        item { PrimaryButton(label = "Book Now", onClick = {}, modifier = Modifier.fillMaxWidth(), isLoading = true) }
        item {
            PrimaryButton(
                label = "Cancel Booking",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                isDestructive = true,
            )
        }
        item {
            PrimaryButton(
                label = "Search Properties",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
            )
        }

        item { ShowcaseSectionHeader(text = "Tonal Button") }
        item { TonalButton(label = "Explore", onClick = {}, modifier = Modifier.fillMaxWidth()) }
        item { TonalButton(label = "Explore", onClick = {}, modifier = Modifier.fillMaxWidth(), isEnabled = false) }
        item { TonalButton(label = "Explore", onClick = {}, modifier = Modifier.fillMaxWidth(), isLoading = true) }
        item {
            TonalButton(
                label = "Search Properties",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            )
        }

        item { ShowcaseSectionHeader(text = "Outlined Button") }
        item { OutlinedButton(label = "View Details", onClick = {}, modifier = Modifier.fillMaxWidth()) }
        item {
            OutlinedButton(
                label = "View Details",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                isEnabled = false,
            )
        }
        item {
            OutlinedButton(
                label = "View Details",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                isLoading = true,
            )
        }
        item {
            OutlinedButton(
                label = "Search Properties",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            )
        }

        item { ShowcaseSectionHeader(text = "Text Button") }
        item { TextButton(label = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth()) }
        item { TextButton(label = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth(), isEnabled = false) }
        item { TextButton(label = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth(), isLoading = true) }
        item { TextButton(label = "Remove", onClick = {}, modifier = Modifier.fillMaxWidth(), isDestructive = true) }
        item {
            TextButton(
                label = "Search Properties",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            )
        }

        item {
            androidx.compose.foundation.layout
                .Spacer(modifier = Modifier.padding(bottom = Dimens.spacing16))
        }
    }
}

@Composable
private fun ShowcaseSectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = Dimens.spacing12),
    )
}

// region Previews

@PreviewThemes
@Composable
private fun ProfileSectionPreview() {
    StayScoutTheme {
        Surface { ProfileSection() }
    }
}

// endregion
