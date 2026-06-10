package com.example.stayout.presentation.list

import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.presentation.base.BaseState

sealed interface PropertyListState : BaseState {
    data object Loading : PropertyListState

    data class Success(
        val location: LocationDomainModel,
        val allProperties: List<PropertyDomainModel>,
        val pageEnd: Int,
        val searchQuery: String = "",
        val isRefreshing: Boolean = false,
        val isLoadingMore: Boolean = false,
        val isOffline: Boolean = false,
    ) : PropertyListState {
        val displayedProperties: List<PropertyDomainModel>
            get() =
                if (searchQuery.isBlank()) {
                    allProperties.take(pageEnd)
                } else {
                    allProperties.filter { it.name.contains(searchQuery, ignoreCase = true) }
                }

        val canLoadMore: Boolean
            get() = searchQuery.isBlank() && pageEnd < allProperties.size
    }

    data class Error(
        val message: String,
    ) : PropertyListState
}
