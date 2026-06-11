package com.example.stayout.presentation.detail

import com.example.stayout.domain.model.CurrencyDomainModel
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.base.BaseState
import com.example.stayout.presentation.util.UgandaLocation

sealed interface PropertyDetailState : BaseState {
    data object Loading : PropertyDetailState

    data class Success(
        val property: PropertyDomainModel,
        val rates: ExchangeRatesDomainModel,
        val mapLocation: UgandaLocation,
        val selectedCurrency: CurrencyDomainModel = CurrencyDomainModel.EUR,
        val ratesUnavailable: Boolean = false,
        val isOffline: Boolean = false,
    ) : PropertyDetailState

    data class Error(
        val message: String,
    ) : PropertyDetailState
}
