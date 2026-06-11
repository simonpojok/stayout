package com.example.stayout.presentation.detail

import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.presentation.base.BaseIntent

sealed interface PropertyDetailIntent : BaseIntent {
    data class SelectCurrency(
        val currency: CurrencyDomainModel,
    ) : PropertyDetailIntent

    data object Retry : PropertyDetailIntent

    data object Book : PropertyDetailIntent

    data object Share : PropertyDetailIntent

    data object Favorite : PropertyDetailIntent

    data object Location : PropertyDetailIntent
}
