package com.example.stayout.presentation.list

import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.base.BaseIntent

sealed interface PropertyListIntent : BaseIntent {
    data object Load : PropertyListIntent

    data object Refresh : PropertyListIntent

    data object LoadMore : PropertyListIntent

    data class SelectProperty(
        val property: PropertyDomainModel,
    ) : PropertyListIntent

    data class UpdateSearch(
        val query: String,
    ) : PropertyListIntent

    data object ToggleTheme : PropertyListIntent
}
