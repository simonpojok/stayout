package com.example.stayout.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme
import kotlin.math.roundToInt

@Composable
fun CurrencySelector(
    selectedCurrency: CurrencyDomainModel,
    onCurrencySelect: (CurrencyDomainModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currencies = CurrencyDomainModel.entries
    val selectedIndex = currencies.indexOf(selectedCurrency).coerceAtLeast(0)

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current
    val insetPx = with(density) { Dimens.spacing4.toPx() }

    val tabWidthPx =
        if (containerSize.width > 0) {
            (containerSize.width - insetPx * 2) / currencies.size
        } else {
            0f
        }
    val tabHeightPx = (containerSize.height - insetPx * 2).coerceAtLeast(0f)

    val indicatorOffsetXPx by animateFloatAsState(
        targetValue = tabWidthPx * selectedIndex,
        animationSpec =
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow,
            ),
        label = "currency_selector_indicator",
    )

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .onSizeChanged { containerSize = it }
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small,
                ).semantics(mergeDescendants = false) { contentDescription = "Currency selector" },
    ) {
        if (tabWidthPx > 0f && tabHeightPx > 0f) {
            Box(
                modifier =
                    Modifier
                        .offset {
                            IntOffset(
                                x = (insetPx + indicatorOffsetXPx).roundToInt(),
                                y = insetPx.roundToInt(),
                            )
                        }.size(
                            width = with(density) { tabWidthPx.toDp() },
                            height = with(density) { tabHeightPx.toDp() },
                        ).border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.extraSmall,
                        ).background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.extraSmall,
                        ),
            )
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.spacing4),
        ) {
            currencies.forEachIndexed { index, currency ->
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .semantics {
                                role = Role.Tab
                                selected = index == selectedIndex
                            }.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { onCurrencySelect(currency) }
                            .padding(vertical = Dimens.spacing8),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = currency.displayName,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color =
                            if (index == selectedIndex) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@PreviewThemes
@Composable
private fun CurrencySelectorEurPreview() {
    StayScoutTheme {
        CurrencySelector(
            selectedCurrency = CurrencyDomainModel.EUR,
            onCurrencySelect = {},
        )
    }
}

@PreviewThemes
@Composable
private fun CurrencySelectorUsdPreview() {
    StayScoutTheme {
        CurrencySelector(
            selectedCurrency = CurrencyDomainModel.USD,
            onCurrencySelect = {},
        )
    }
}
