package com.example.stayout.presentation.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Deck
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Weekend
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
            FacilityMeta(icon = Icons.Filled.Wifi, descriptionRes = R.string.facility_desc_wifi),
        listOf("tv", "television") to
            FacilityMeta(icon = Icons.Filled.Tv, descriptionRes = R.string.facility_desc_tv),
        listOf("bar", "drink", "beer") to
            FacilityMeta(icon = Icons.Filled.LocalBar, descriptionRes = R.string.facility_desc_bar),
        listOf("game", "gaming", "play") to
            FacilityMeta(icon = Icons.Filled.SportsEsports, descriptionRes = R.string.facility_desc_game),
        listOf("patio", "terrace", "balcony", "outdoor") to
            FacilityMeta(icon = Icons.Filled.Deck, descriptionRes = R.string.facility_desc_patio),
        listOf("check-in", "reception", "front desk", "24h") to
            FacilityMeta(icon = Icons.Filled.SupportAgent, descriptionRes = R.string.facility_desc_reception),
        listOf("locker", "lock", "safe") to
            FacilityMeta(icon = Icons.Filled.Lock, descriptionRes = R.string.facility_desc_locker),
        listOf("luggage", "storage", "bag") to
            FacilityMeta(icon = Icons.Filled.Work, descriptionRes = R.string.facility_desc_luggage),
        listOf("pool", "swim") to
            FacilityMeta(icon = Icons.Filled.Pool, descriptionRes = R.string.facility_desc_pool),
        listOf("gym", "fitness", "workout") to
            FacilityMeta(icon = Icons.Filled.FitnessCenter, descriptionRes = R.string.facility_desc_gym),
        listOf("kitchen", "cook") to
            FacilityMeta(icon = Icons.Filled.Kitchen, descriptionRes = R.string.facility_desc_kitchen),
        listOf("parking", "car park") to
            FacilityMeta(icon = Icons.Filled.LocalParking, descriptionRes = R.string.facility_desc_parking),
        listOf("laundry", "wash") to
            FacilityMeta(icon = Icons.Filled.LocalLaundryService, descriptionRes = R.string.facility_desc_laundry),
        listOf("café", "cafe", "coffee") to
            FacilityMeta(icon = Icons.Filled.LocalCafe, descriptionRes = R.string.facility_desc_cafe),
        listOf("restaurant", "dining", "food") to
            FacilityMeta(icon = Icons.Filled.Restaurant, descriptionRes = R.string.facility_desc_restaurant),
        listOf("breakfast") to
            FacilityMeta(icon = Icons.Filled.Restaurant, descriptionRes = R.string.facility_desc_breakfast),
        listOf("shuttle", "airport") to
            FacilityMeta(icon = Icons.Filled.AirportShuttle, descriptionRes = R.string.facility_desc_shuttle),
        listOf("air conditioning", " ac ", "cooling") to
            FacilityMeta(icon = Icons.Filled.AcUnit, descriptionRes = R.string.facility_desc_ac),
        listOf("bike", "cycle") to
            FacilityMeta(
                icon = Icons.AutoMirrored.Filled.DirectionsBike,
                descriptionRes = R.string.facility_desc_bike,
            ),
        listOf("medical", "first aid", "health") to
            FacilityMeta(icon = Icons.Filled.LocalHospital, descriptionRes = R.string.facility_desc_medical),
    )

private val defaultMeta =
    FacilityMeta(
        icon = Icons.Filled.CheckCircle,
        descriptionRes = R.string.facility_desc_default,
    )

private fun metaFor(name: String): FacilityMeta =
    facilityMappings
        .firstOrNull { (keywords, _) -> keywords.any { name.containsKeyword(it) } }
        ?.second ?: defaultMeta

fun facilityIconFor(name: String): ImageVector = metaFor(name).icon

@StringRes
fun facilityDescriptionRes(name: String): Int = metaFor(name).descriptionRes

// ------- ID-based lookups (precise, from API facility IDs) -------

private val facilityIdIconMap: Map<String, ImageVector> =
    mapOf(
        "FREEWIFI" to Icons.Filled.Wifi,
        "WIFI" to Icons.Filled.Wifi,
        "FREEINTERNETACCESS" to Icons.Filled.Wifi,
        "INTERNETACCESS" to Icons.Filled.Wifi,
        "HOTSHOWERS" to Icons.Filled.Shower,
        "KITCHEN" to Icons.Filled.Kitchen,
        "MICROWAVE" to Icons.Filled.Kitchen,
        "COOKER" to Icons.Filled.Kitchen,
        "FRIDGEFREEZER" to Icons.Filled.Kitchen,
        "LOCKERS" to Icons.Filled.Lock,
        "KEYCARDACCESS" to Icons.Filled.Lock,
        "SAFEDEPOSITBOX" to Icons.Filled.Lock,
        "ELEVATOR" to Icons.Filled.Elevator,
        "WASHINGMACHINE" to Icons.Filled.LocalLaundryService,
        "DRYER" to Icons.Filled.LocalLaundryService,
        "LAUNDRYFACILITIES" to Icons.Filled.LocalLaundryService,
        "IRONIRONINGBOARD" to Icons.Filled.LocalLaundryService,
        "BREAKFASTINCLUDED" to Icons.Filled.FreeBreakfast,
        "LINENINCLUDED" to Icons.Filled.Bed,
        "FREEPARKING" to Icons.Filled.LocalParking,
        "PARKING" to Icons.Filled.LocalParking,
        "24HOURRECEPTION" to Icons.Filled.SupportAgent,
        "24HOURSECURITY" to Icons.Filled.Security,
        "AIRPORTTRANSFERS" to Icons.Filled.AirportShuttle,
        "SHUTTLEBUS" to Icons.Filled.AirportShuttle,
        "BICYCLEHIRE" to Icons.AutoMirrored.Filled.DirectionsBike,
        "BICYCLEPARKING" to Icons.AutoMirrored.Filled.DirectionsBike,
        "PLAYSTATION" to Icons.Filled.SportsEsports,
        "GAMESROOM" to Icons.Filled.SportsEsports,
        "BOARDGAMES" to Icons.Filled.SportsEsports,
        "RESTAURANT" to Icons.Filled.Restaurant,
        "MEALSAVAILABLE" to Icons.Filled.Restaurant,
        "BAR" to Icons.Filled.LocalBar,
        "MINIBAR" to Icons.Filled.LocalBar,
        "CAFE" to Icons.Filled.LocalCafe,
        "TEACOFFEEMAKINGFACILITIES" to Icons.Filled.LocalCafe,
        "COTSAVAILABLE" to Icons.Filled.ChildFriendly,
        "AIRCONDITIONING" to Icons.Filled.AcUnit,
        "LUGGAGESTORAGE" to Icons.Filled.Work,
        "TOURSTRAVELDESK" to Icons.Filled.TravelExplore,
        "COMMONROOM" to Icons.Filled.Weekend,
        "FREECITYTOUR" to Icons.Filled.Explore,
        "FREECITYMAPS" to Icons.Filled.Explore,
        "POOL" to Icons.Filled.Pool,
        "POOLTABLE" to Icons.Filled.Pool,
        "FUSBALL" to Icons.Filled.SportsSoccer,
        "HOUSEKEEPING" to Icons.Filled.CleaningServices,
        "SANITISATIONBADGE" to Icons.Filled.CleaningServices,
        "BOOKEXCHANGE" to Icons.AutoMirrored.Filled.MenuBook,
        "GYM" to Icons.Filled.FitnessCenter,
        "FITNESSCENTER" to Icons.Filled.FitnessCenter,
        "TV" to Icons.Filled.Tv,
        "TVROOM" to Icons.Filled.Tv,
    )

fun facilityIconForId(id: String): ImageVector = facilityIdIconMap[id.uppercase()] ?: Icons.Filled.CheckCircle

private val facilityCategoryIconMap: Map<String, ImageVector> =
    mapOf(
        "FACILITYCATEGORYFREE" to Icons.Filled.CardGiftcard,
        "FACILITYCATEGORYGENERAL" to Icons.Filled.Category,
        "FACILITYCATEGORYSERVICES" to Icons.Filled.RoomService,
        "FACILITYCATEGORYFOODANDDRINK" to Icons.Filled.DinnerDining,
        "FACILITYCATEGORYENTERTAINMENT" to Icons.Filled.LiveTv,
    )

fun facilityCategoryIconFor(categoryId: String): ImageVector =
    facilityCategoryIconMap[categoryId.uppercase()] ?: Icons.Filled.Category
