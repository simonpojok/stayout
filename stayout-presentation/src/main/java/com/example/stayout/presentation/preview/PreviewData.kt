package com.example.stayout.presentation.preview

import com.example.stayout.domain.model.ExchangeRatesDomainModel
import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import com.example.stayout.domain.model.LocationDomainModel
import com.example.stayout.domain.model.PromotionDomainModel
import com.example.stayout.domain.model.PromotionType
import com.example.stayout.domain.model.PropertyDomainModel
import com.example.stayout.domain.model.RatingBreakdownDomainModel
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
            imageUrls = emptyList(),
            address = "2-12 Lord Edward St, Dublin, Ireland",
            type = "Hostel",
            facilities =
                listOf(
                    FacilityCategoryDomainModel(
                        id = "FACILITYCATEGORYGENERAL",
                        name = "Common Areas",
                        facilities =
                            listOf(
                                FacilityDomainModel(id = "TVROOM", name = "TV Room"),
                                FacilityDomainModel(id = "BAR", name = "Bar"),
                                FacilityDomainModel(id = "GAMESROOM", name = "Game Room"),
                                FacilityDomainModel(id = "OUTDOORTERRACE", name = "Patio"),
                            ),
                    ),
                    FacilityCategoryDomainModel(
                        id = "FACILITYCATEGORYSERVICES",
                        name = "Services",
                        facilities =
                            listOf(
                                FacilityDomainModel(id = "FREEWIFI", name = "Free WiFi"),
                                FacilityDomainModel(id = "24HOURRECEPTION", name = "24h Check-in"),
                                FacilityDomainModel(id = "LOCKERS", name = "Lockers"),
                                FacilityDomainModel(id = "LUGGAGESTORAGE", name = "Luggage Storage"),
                            ),
                    ),
                ),
            freeCancellationAvailable = true,
            latitude = 53.3437259,
            longitude = -6.269898,
            distanceKm = 1.01,
            dormPriceValue = BigDecimal("14.18"),
            privatePriceValue = BigDecimal("55.76"),
            ratingBreakdown =
                RatingBreakdownDomainModel(
                    security = 7.4,
                    location = 8.6,
                    staff = 8.2,
                    funScore = 6.7,
                    cleanliness = 6.8,
                    facilities = 6.8,
                    value = 7.3,
                    ratingsCount = 11133,
                ),
            promotions =
                listOf(
                    PromotionDomainModel(type = PromotionType.MOBILE, label = "Mobile Deal", discount = 10),
                ),
            averagePriceValue = BigDecimal("12.76"),
            originalPriceValue = BigDecimal("14.18"),
            totalDiscount = BigDecimal("1.42"),
            district = "Temple Bar",
            isRecommended = true,
            starRating = 0,
            freeCancellationUntil = "2024-11-16",
            minimumStayDescription = null,
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
            imageUrls = emptyList(),
            address = "7-9 Aston Quay, Dublin 2, Ireland",
            type = "Hostel",
            facilities =
                listOf(
                    FacilityCategoryDomainModel(
                        id = "FACILITYCATEGORYSERVICES",
                        name = "Services",
                        facilities =
                            listOf(
                                FacilityDomainModel(id = "FREEWIFI", name = "Free WiFi"),
                                FacilityDomainModel(id = "LOCKERS", name = "Lockers"),
                            ),
                    ),
                ),
            freeCancellationAvailable = false,
            latitude = 53.3474366,
            longitude = -6.2603459,
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
