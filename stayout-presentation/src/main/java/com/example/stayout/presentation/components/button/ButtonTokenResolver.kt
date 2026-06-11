package com.example.stayout.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stayout.presentation.theme.Dimens

enum class ButtonSize { ExtraSmall, Small, Medium, Large }

internal enum class ButtonStyle { Filled, Tonal, Outlined, Text }

internal sealed class ButtonContent {
    @Stable
    data class Label(
        val label: String,
        val isLoading: Boolean = false,
        val leadingIcon: (@Composable () -> Unit)? = null,
        val trailingIcon: (@Composable () -> Unit)? = null,
    ) : ButtonContent()

    @Stable
    data class Custom(
        val content: @Composable RowScope.() -> Unit,
    ) : ButtonContent()
}

internal object ButtonTokenResolver {
    @Composable
    fun resolveColors(
        style: ButtonStyle,
        isDestructive: Boolean,
    ): ButtonColors =
        when {
            style == ButtonStyle.Filled && isDestructive ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
                    disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.38f),
                )
            style == ButtonStyle.Text && isDestructive ->
                ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.38f),
                )
            style == ButtonStyle.Filled -> ButtonDefaults.buttonColors()
            style == ButtonStyle.Tonal -> ButtonDefaults.filledTonalButtonColors()
            style == ButtonStyle.Outlined -> ButtonDefaults.outlinedButtonColors()
            else -> ButtonDefaults.textButtonColors()
        }

    @Composable
    fun resolveBorder(
        style: ButtonStyle,
        isEnabled: Boolean,
    ): BorderStroke? {
        if (style != ButtonStyle.Outlined) return null
        return BorderStroke(
            width = 1.dp,
            color =
                if (isEnabled) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                },
        )
    }

    fun resolvePadding(size: ButtonSize): PaddingValues =
        when (size) {
            ButtonSize.ExtraSmall -> PaddingValues(horizontal = Dimens.spacing8, vertical = Dimens.spacing6)
            ButtonSize.Small -> PaddingValues(horizontal = Dimens.spacing16, vertical = Dimens.spacing8)
            ButtonSize.Medium -> PaddingValues(horizontal = Dimens.spacing20, vertical = Dimens.spacing12)
            ButtonSize.Large -> PaddingValues(horizontal = Dimens.spacing24, vertical = Dimens.spacing14)
        }

    @Composable
    fun resolveTextStyle(size: ButtonSize): TextStyle {
        val base =
            when (size) {
                ButtonSize.ExtraSmall -> MaterialTheme.typography.bodySmall
                ButtonSize.Small -> MaterialTheme.typography.bodyMedium
                ButtonSize.Medium -> MaterialTheme.typography.titleMedium
                ButtonSize.Large -> MaterialTheme.typography.bodyLarge
            }
        return base.copy(fontWeight = FontWeight.SemiBold, color = Color.Unspecified)
    }

    fun resolveIconSize(size: ButtonSize): Dp =
        when (size) {
            ButtonSize.ExtraSmall -> Dimens.spacing16
            ButtonSize.Small -> Dimens.spacing20
            ButtonSize.Medium -> Dimens.spacing20
            ButtonSize.Large -> Dimens.spacing24
        }
}
