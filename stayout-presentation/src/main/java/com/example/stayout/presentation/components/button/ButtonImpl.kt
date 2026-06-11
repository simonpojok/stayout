package com.example.stayout.presentation.components.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.stayout.presentation.theme.Dimens
import kotlinx.coroutines.launch

@Composable
internal fun ButtonImpl(
    onClick: () -> Unit,
    style: ButtonStyle,
    size: ButtonSize,
    buttonContent: ButtonContent,
    modifier: Modifier = Modifier,
    isDestructive: Boolean = false,
    isEnabled: Boolean = true,
) {
    val shape = MaterialTheme.shapes.small
    val border = ButtonTokenResolver.resolveBorder(style, isEnabled)
    val colors = ButtonTokenResolver.resolveColors(style, isDestructive)
    val contentPadding = ButtonTokenResolver.resolvePadding(size)

    val isLoading =
        when (buttonContent) {
            is ButtonContent.Label -> buttonContent.isLoading
            is ButtonContent.Custom -> false
        }

    val buttonScale = remember { Animatable(BUTTON_RESTING_SCALE) }
    val scope = rememberCoroutineScope()

    val animatedOnClick = {
        scope.launch {
            launch { onClick() }
            buttonScale.animateTo(
                targetValue = BUTTON_PRESSED_SCALE,
                animationSpec = tween(durationMillis = PRESS_DURATION_MS, easing = FastOutLinearInEasing),
            )
            buttonScale.animateTo(
                targetValue = BUTTON_RESTING_SCALE,
                animationSpec = tween(durationMillis = PRESS_DURATION_MS, easing = FastOutLinearInEasing),
            )
        }
        Unit
    }

    val buttonModifier =
        modifier
            .graphicsLayer {
                scaleX = buttonScale.value
                scaleY = buttonScale.value
            }.defaultMinSize(minHeight = 1.dp)

    val resolvedContent: @Composable RowScope.() -> Unit = {
        when (buttonContent) {
            is ButtonContent.Label ->
                ButtonLabelContent(
                    label = buttonContent.label,
                    textStyle = ButtonTokenResolver.resolveTextStyle(size),
                    isLoading = isLoading,
                    leadingIcon = buttonContent.leadingIcon,
                    trailingIcon = buttonContent.trailingIcon,
                    iconSize = ButtonTokenResolver.resolveIconSize(size),
                )
            is ButtonContent.Custom -> buttonContent.content(this)
        }
    }

    when (style) {
        ButtonStyle.Filled ->
            Button(
                onClick = animatedOnClick,
                enabled = isEnabled,
                modifier = buttonModifier,
                shape = shape,
                colors = colors,
                contentPadding = contentPadding,
                content = resolvedContent,
            )
        ButtonStyle.Tonal ->
            FilledTonalButton(
                onClick = animatedOnClick,
                enabled = isEnabled,
                modifier = buttonModifier,
                shape = shape,
                colors = colors,
                contentPadding = contentPadding,
                content = resolvedContent,
            )
        ButtonStyle.Outlined ->
            OutlinedButton(
                onClick = animatedOnClick,
                enabled = isEnabled,
                modifier = buttonModifier,
                shape = shape,
                colors = colors,
                border = border,
                contentPadding = contentPadding,
                content = resolvedContent,
            )
        ButtonStyle.Text ->
            TextButton(
                onClick = animatedOnClick,
                enabled = isEnabled,
                modifier = buttonModifier,
                shape = shape,
                colors = colors,
                contentPadding = contentPadding,
                content = resolvedContent,
            )
    }
}

@Composable
private fun ButtonLabelContent(
    label: String,
    textStyle: androidx.compose.ui.text.TextStyle,
    isLoading: Boolean,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    iconSize: androidx.compose.ui.unit.Dp,
) {
    val labelAlpha by animateFloatAsState(
        targetValue = if (isLoading) 0f else 1f,
        animationSpec =
            tween(
                durationMillis = CROSSFADE_DURATION_MS,
                delayMillis = if (isLoading) 0 else STAGGER_DELAY_MS,
            ),
        label = "button_label_alpha",
    )
    val spinnerAlpha by animateFloatAsState(
        targetValue = if (isLoading) 1f else 0f,
        animationSpec =
            tween(
                durationMillis = CROSSFADE_DURATION_MS,
                delayMillis = if (isLoading) STAGGER_DELAY_MS else 0,
            ),
        label = "button_spinner_alpha",
    )

    Box(contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.graphicsLayer { alpha = labelAlpha },
        ) {
            if (leadingIcon != null) {
                Box(modifier = Modifier.size(iconSize), contentAlignment = Alignment.Center) {
                    leadingIcon()
                }
                Spacer(Modifier.width(Dimens.spacing4))
            }
            Text(text = label, style = textStyle)
            if (trailingIcon != null) {
                Spacer(Modifier.width(Dimens.spacing4))
                Box(modifier = Modifier.size(iconSize), contentAlignment = Alignment.Center) {
                    trailingIcon()
                }
            }
        }

        CircularProgressIndicator(
            modifier =
                Modifier
                    .size(iconSize)
                    .graphicsLayer { alpha = spinnerAlpha },
            color = LocalContentColor.current,
            strokeWidth = Dimens.spacing2,
        )
    }
}

private const val BUTTON_RESTING_SCALE = 1.0f
private const val BUTTON_PRESSED_SCALE = 0.95f
private const val PRESS_DURATION_MS = 100
private const val CROSSFADE_DURATION_MS = 150
private const val STAGGER_DELAY_MS = 100
