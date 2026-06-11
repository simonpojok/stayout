package com.example.stayout.presentation.home.sections.explore

import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.base.BaseIntent

sealed interface ExploreIntent : BaseIntent {
    data object Load : ExploreIntent

    data object Refresh : ExploreIntent

    data object LoadMore : ExploreIntent

    data class SelectProperty(
        val property: PropertyDomainModel,
    ) : ExploreIntent

    data class UpdateSearch(
        val query: String,
    ) : ExploreIntent
}
