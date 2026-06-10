package com.example.stayout.presentation.preview

import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PropertyDomainModel
import java.math.BigDecimal

object PreviewData {
    val previewProperty =
        PropertyDomainModel(
            id = 1,
            name = "Kinlay House Hostel",
            isFeatured = true,
            rating = 8.5,
            ratingCount = "234",
            lowestPriceValue = BigDecimal("14.18"),
            lowestPriceCurrency = "EUR",
            overview =
                "<p>A cosy hostel right in the heart of Dublin, steps from Temple Bar and all major attractions.</p>",
            thumbnailUrl = null,
            address = "2-12 Lord Edward St, Dublin, Ireland",
            type = "Hostel",
            facilities =
                listOf(
                    FacilityCategoryDomainModel("Common Areas", listOf("TV Room", "Bar", "Game Room", "Patio")),
                    FacilityCategoryDomainModel(
                        "Services",
                        listOf("Free WiFi", "24h Check-in", "Lockers", "Luggage Storage"),
                    ),
                ),
            freeCancellationAvailable = true,
        )

    val previewPropertyNoFeature =
        PropertyDomainModel(
            id = 2,
            name = "Abigail's Hostel",
            isFeatured = false,
            rating = 7.2,
            ratingCount = "89",
            lowestPriceValue = BigDecimal("22.50"),
            lowestPriceCurrency = "EUR",
            overview = "<p>Comfortable budget accommodation in Dublin city centre.</p>",
            thumbnailUrl = null,
            address = "7-9 Aston Quay, Dublin 2, Ireland",
            type = "Hostel",
            facilities =
                listOf(
                    FacilityCategoryDomainModel("Services", listOf("Free WiFi", "Lockers")),
                ),
            freeCancellationAvailable = false,
        )

    val previewLocation =
        LocationDomainModel(
            cityName = "Dublin",
            countryName = "Ireland",
        )

    val previewRates =
        ExchangeRatesDomainModel(
            usd = BigDecimal("1.08"),
            gbp = BigDecimal("0.86"),
        )
}
