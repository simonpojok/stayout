package com.example.stayout.presentation.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun CurrencySelector(
    selectedCurrency: CurrencyDomainModel,
    onCurrencySelect: (CurrencyDomainModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currencies = CurrencyDomainModel.entries
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        currencies.forEachIndexed { index, currency ->
            SegmentedButton(
                selected = currency == selectedCurrency,
                onClick = { onCurrencySelect(currency) },
                shape =
                    SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = currencies.size,
                    ),
            ) {
                Text(currency.displayName)
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
