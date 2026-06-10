package com.example.stayout.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.usecase.GetExchangeRatesUseCase
import com.example.stayout.domain.usecase.GetPropertyByIdUseCase
import com.example.stayout.domain.usecase.ObserveNetworkStatusUseCase
import com.example.stayout.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel
    @Inject
    constructor(
        private val getPropertyByIdUseCase: GetPropertyByIdUseCase,
        private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
        private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<PropertyDetailState, PropertyDetailIntent, PropertyDetailEvent>(
            initialState = PropertyDetailState.Loading,
        ) {
        private val propertyId: Int =
            checkNotNull(savedStateHandle[ARG_PROPERTY_ID]) {
                "propertyId nav argument is missing"
            }

        init {
            loadData()
            observeNetworkStatus()
        }

        override fun onIntent(intent: PropertyDetailIntent) {
            when (intent) {
                is PropertyDetailIntent.SelectCurrency ->
                    updateState {
                        (this as? PropertyDetailState.Success)?.copy(selectedCurrency = intent.currency) ?: this
                    }
                PropertyDetailIntent.Retry -> loadData()
            }
        }

        private fun observeNetworkStatus() {
            viewModelScope.launch {
                observeNetworkStatusUseCase()
                    .onEach { isOnline ->
                        updateState {
                            (this as? PropertyDetailState.Success)?.copy(isOffline = !isOnline) ?: this
                        }
                    }.launchIn(this)
            }
        }

        private fun loadData() {
            viewModelScope.launch {
                updateState { PropertyDetailState.Loading }

                val propertyDeferred = async { getPropertyByIdUseCase(propertyId) }
                val ratesDeferred = async { getExchangeRatesUseCase() }

                val property = propertyDeferred.await()
                val ratesResult = ratesDeferred.await()

                if (property == null) {
                    updateState { PropertyDetailState.Error("Property not found") }
                    return@launch
                }

                val rates =
                    ratesResult.getOrNull() ?: ExchangeRatesDomainModel(
                        usd = BigDecimal.ONE,
                        gbp = BigDecimal.ONE,
                    )

                updateState {
                    PropertyDetailState.Success(
                        property = property,
                        rates = rates,
                        ratesUnavailable = ratesResult.isFailure,
                    )
                }
            }
        }

        companion object {
            const val ARG_PROPERTY_ID = "propertyId"
        }
    }
