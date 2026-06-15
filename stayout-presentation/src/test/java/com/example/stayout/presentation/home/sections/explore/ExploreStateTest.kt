package com.example.stayout.presentation.home.sections.explore

import com.example.stayout.domain.model.InternetConnectionError
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class ExploreStateTest {
    private val location = LocationDomainModel("Dublin", "Ireland")

    private fun property(
        id: Int,
        name: String,
    ) = PropertyDomainModel(
        id = id,
        name = name,
        isFeatured = false,
        rating = 8.0,
        ratingCount = "0",
        lowestPriceValue = BigDecimal("10.00"),
        lowestPriceCurrency = "EUR",
        overview = "",
        thumbnailUrl = null,
        address = "",
        type = "Hostel",
        facilities = emptyList(),
        freeCancellationAvailable = false,
    )

    private val properties =
        listOf(
            property(1, "Kinlay House"),
            property(2, "Generator Hostel"),
            property(3, "Abbey Court"),
        )

    @Test
    fun `displayedProperties returns paged results when search query is blank`() {
        val state = ExploreState.Success(location = location, allProperties = properties, pageEnd = 2)
        assertEquals(properties.take(2), state.displayedProperties)
    }

    @Test
    fun `displayedProperties returns matching results when search query is set`() {
        val state =
            ExploreState.Success(
                location = location,
                allProperties = properties,
                pageEnd = 2,
                searchQuery = "Kinlay",
            )
        assertEquals(listOf(properties[0]), state.displayedProperties)
    }

    @Test
    fun `displayedProperties is empty when allProperties is empty`() {
        val state = ExploreState.Success(location = location, allProperties = emptyList(), pageEnd = 0)
        assertTrue(state.displayedProperties.isEmpty())
    }

    @Test
    fun `displayedProperties is empty when search query has no matches`() {
        val state =
            ExploreState.Success(
                location = location,
                allProperties = properties,
                pageEnd = 2,
                searchQuery = "Nonexistent",
            )
        assertTrue(state.displayedProperties.isEmpty())
    }

    @Test
    fun `canLoadMore is true when search is blank and more pages remain`() {
        val state = ExploreState.Success(location = location, allProperties = properties, pageEnd = 2)
        assertTrue(state.canLoadMore)
    }

    @Test
    fun `canLoadMore is false when pageEnd reaches the end of the list`() {
        val state =
            ExploreState.Success(
                location = location,
                allProperties = properties,
                pageEnd = properties.size,
            )
        assertFalse(state.canLoadMore)
    }

    @Test
    fun `canLoadMore is false when a search query is active`() {
        val state =
            ExploreState.Success(
                location = location,
                allProperties = properties,
                pageEnd = 1,
                searchQuery = "Kinlay",
            )
        assertFalse(state.canLoadMore)
    }

    @Test
    fun `Loading state defaults to online`() {
        val state: ExploreState = ExploreState.Loading()
        assertTrue(state is ExploreState.Loading)
        assertFalse(state.isOffline)
    }

    @Test
    fun `Error state holds the provided error`() {
        val state = ExploreState.Error(InternetConnectionError.NoConnection)
        assertEquals(InternetConnectionError.NoConnection, state.error)
    }
}
