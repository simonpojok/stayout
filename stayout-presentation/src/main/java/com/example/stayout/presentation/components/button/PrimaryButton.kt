package com.example.stayout.presentation.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Large,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    isDestructive: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) = ButtonImpl(
    onClick = onClick,
    style = ButtonStyle.Filled,
    size = size,
    modifier = modifier,
    isEnabled = isEnabled,
    isDestructive = isDestructive,
    buttonContent = ButtonContent.Label(label, isLoading, leadingIcon, trailingIcon),
)

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Large,
    isEnabled: Boolean = true,
    isDestructive: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) = ButtonImpl(
    onClick = onClick,
    style = ButtonStyle.Filled,
    size = size,
    modifier = modifier,
    isEnabled = isEnabled,
    isDestructive = isDestructive,
    buttonContent = ButtonContent.Custom(content),
)

@PreviewThemes
@Composable
private fun PrimaryButtonPreview() {
    StayScoutTheme {
        Column(
            modifier = Modifier.padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        ) {
            PrimaryButton(label = "Book Now", onClick = {}, modifier = Modifier.fillMaxWidth())
            PrimaryButton(label = "Book Now", onClick = {}, modifier = Modifier.fillMaxWidth(), isEnabled = false)
            PrimaryButton(label = "Book Now", onClick = {}, modifier = Modifier.fillMaxWidth(), isLoading = true)
            PrimaryButton(
                label = "Cancel Booking",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                isDestructive = true,
            )
            PrimaryButton(
                label = "Search",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
            )
        }
    }
}
