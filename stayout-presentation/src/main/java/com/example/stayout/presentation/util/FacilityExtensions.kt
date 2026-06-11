package com.example.stayout.presentation.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Deck
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.stayout.presentation.R

private fun String.containsKeyword(keyword: String) = contains(keyword, ignoreCase = true)

private data class FacilityMeta(
    val icon: ImageVector,
    @StringRes val descriptionRes: Int,
)

private val facilityMappings: List<Pair<List<String>, FacilityMeta>> =
    listOf(
        listOf("wifi", "wi-fi", "internet") to
            FacilityMeta(
                icon = Icons.Filled.Wifi,
                descriptionRes = R.string.facility_desc_wifi,
            ),
        listOf("tv", "television") to
            FacilityMeta(
                icon = Icons.Filled.Tv,
                descriptionRes = R.string.facility_desc_tv,
            ),
        listOf("bar", "drink", "beer") to
            FacilityMeta(
                icon = Icons.Filled.LocalBar,
                descriptionRes = R.string.facility_desc_bar,
            ),
        listOf("game", "gaming", "play") to
            FacilityMeta(
                icon = Icons.Filled.SportsEsports,
                descriptionRes = R.string.facility_desc_game,
            ),
        listOf("patio", "terrace", "balcony", "outdoor") to
            FacilityMeta(
                icon = Icons.Filled.Deck,
                descriptionRes = R.string.facility_desc_patio,
            ),
        listOf("check-in", "reception", "front desk", "24h") to
            FacilityMeta(
                icon = Icons.Filled.SupportAgent,
                descriptionRes = R.string.facility_desc_reception,
            ),
        listOf("locker", "lock", "safe") to
            FacilityMeta(
                icon = Icons.Filled.Lock,
                descriptionRes = R.string.facility_desc_locker,
            ),
        listOf("luggage", "storage", "bag") to
            FacilityMeta(
                icon = Icons.Filled.Work,
                descriptionRes = R.string.facility_desc_luggage,
            ),
        listOf("pool", "swim") to
            FacilityMeta(
                icon = Icons.Filled.Pool,
                descriptionRes = R.string.facility_desc_pool,
            ),
        listOf("gym", "fitness", "workout") to
            FacilityMeta(
                icon = Icons.Filled.FitnessCenter,
                descriptionRes = R.string.facility_desc_gym,
            ),
        listOf("kitchen", "cook") to
            FacilityMeta(
                icon = Icons.Filled.Kitchen,
                descriptionRes = R.string.facility_desc_kitchen,
            ),
        listOf("parking", "car park") to
            FacilityMeta(
                icon = Icons.Filled.LocalParking,
                descriptionRes = R.string.facility_desc_parking,
            ),
        listOf("laundry", "wash") to
            FacilityMeta(
                icon = Icons.Filled.LocalLaundryService,
                descriptionRes = R.string.facility_desc_laundry,
            ),
        listOf("café", "cafe", "coffee") to
            FacilityMeta(
                icon = Icons.Filled.LocalCafe,
                descriptionRes = R.string.facility_desc_cafe,
            ),
        listOf("restaurant", "dining", "food") to
            FacilityMeta(
                icon = Icons.Filled.Restaurant,
                descriptionRes = R.string.facility_desc_restaurant,
            ),
        listOf("breakfast") to
            FacilityMeta(
                icon = Icons.Filled.Restaurant,
                descriptionRes = R.string.facility_desc_breakfast,
            ),
        listOf("shuttle", "airport") to
            FacilityMeta(
                icon = Icons.Filled.AirportShuttle,
                descriptionRes = R.string.facility_desc_shuttle,
            ),
        listOf("air conditioning", " ac ", "cooling") to
            FacilityMeta(
                icon = Icons.Filled.AcUnit,
                descriptionRes = R.string.facility_desc_ac,
            ),
        listOf("bike", "cycle") to
            FacilityMeta(
                icon = Icons.AutoMirrored.Filled.DirectionsBike,
                descriptionRes = R.string.facility_desc_bike,
            ),
        listOf(
            "medical",
            "first aid",
            "health",
        ) to
            FacilityMeta(
                icon = Icons.Filled.LocalHospital,
                descriptionRes = R.string.facility_desc_medical,
            ),
    )

private val defaultMeta =
    FacilityMeta(
        icon = Icons.Filled.CheckCircle,
        descriptionRes = R.string.facility_desc_default,
    )

private fun metaFor(name: String): FacilityMeta =
    facilityMappings
        .firstOrNull { (keywords, _) ->
            keywords.any { name.containsKeyword(it) }
        }?.second ?: defaultMeta

fun facilityIconFor(name: String): ImageVector = metaFor(name).icon

@StringRes
fun facilityDescriptionRes(name: String): Int = metaFor(name).descriptionRes
