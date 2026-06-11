package com.example.stayout.presentation.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun TextButton(
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
    style = ButtonStyle.Text,
    size = size,
    modifier = modifier,
    isEnabled = isEnabled,
    isDestructive = isDestructive,
    buttonContent = ButtonContent.Label(label, isLoading, leadingIcon, trailingIcon),
)

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Large,
    isEnabled: Boolean = true,
    isDestructive: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) = ButtonImpl(
    onClick = onClick,
    style = ButtonStyle.Text,
    size = size,
    modifier = modifier,
    isEnabled = isEnabled,
    isDestructive = isDestructive,
    buttonContent = ButtonContent.Custom(content),
)

@PreviewThemes
@Composable
private fun TextButtonPreview() {
    StayScoutTheme {
        Column(
            modifier = Modifier.padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        ) {
            TextButton(label = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth())
            TextButton(label = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth(), isEnabled = false)
            TextButton(label = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth(), isLoading = true)
            TextButton(label = "Remove", onClick = {}, modifier = Modifier.fillMaxWidth(), isDestructive = true)
        }
    }
}
