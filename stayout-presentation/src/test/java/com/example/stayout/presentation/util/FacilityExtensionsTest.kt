package com.example.stayout.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.stayout.presentation.R
import org.junit.Assert.assertEquals
import org.junit.Test

class FacilityExtensionsTest {
    // region facilityIconFor

    @Test
    fun `wifi keyword returns Wifi icon`() {
        assertEquals(Icons.Filled.Wifi, facilityIconFor("Free WiFi"))
    }

    @Test
    fun `wi-fi keyword returns Wifi icon`() {
        assertEquals(Icons.Filled.Wifi, facilityIconFor("wi-fi access"))
    }

    @Test
    fun `internet keyword returns Wifi icon`() {
        assertEquals(Icons.Filled.Wifi, facilityIconFor("internet access"))
    }

    @Test
    fun `tv keyword returns Tv icon`() {
        assertEquals(Icons.Filled.Tv, facilityIconFor("TV lounge"))
    }

    @Test
    fun `bar keyword returns LocalBar icon`() {
        assertEquals(Icons.Filled.LocalBar, facilityIconFor("bar"))
    }

    @Test
    fun `game keyword returns SportsEsports icon`() {
        assertEquals(Icons.Filled.SportsEsports, facilityIconFor("game room"))
    }

    @Test
    fun `pool keyword returns Pool icon`() {
        assertEquals(Icons.Filled.Pool, facilityIconFor("Swimming pool"))
    }

    @Test
    fun `gym keyword returns FitnessCenter icon`() {
        assertEquals(Icons.Filled.FitnessCenter, facilityIconFor("Gym"))
    }

    @Test
    fun `kitchen keyword returns Kitchen icon`() {
        assertEquals(Icons.Filled.Kitchen, facilityIconFor("shared kitchen"))
    }

    @Test
    fun `parking keyword returns LocalParking icon`() {
        assertEquals(Icons.Filled.LocalParking, facilityIconFor("Free parking"))
    }

    @Test
    fun `laundry keyword returns LocalLaundryService icon`() {
        assertEquals(Icons.Filled.LocalLaundryService, facilityIconFor("Laundry service"))
    }

    @Test
    fun `cafe keyword returns LocalCafe icon`() {
        assertEquals(Icons.Filled.LocalCafe, facilityIconFor("café on site"))
    }

    @Test
    fun `restaurant keyword returns Restaurant icon`() {
        assertEquals(Icons.Filled.Restaurant, facilityIconFor("Restaurant"))
    }

    @Test
    fun `breakfast keyword returns Restaurant icon`() {
        assertEquals(Icons.Filled.Restaurant, facilityIconFor("breakfast included"))
    }

    @Test
    fun `shuttle keyword returns AirportShuttle icon`() {
        assertEquals(Icons.Filled.AirportShuttle, facilityIconFor("airport shuttle"))
    }

    @Test
    fun `bike keyword returns DirectionsBike icon`() {
        assertEquals(Icons.AutoMirrored.Filled.DirectionsBike, facilityIconFor("bike rental"))
    }

    @Test
    fun `locker keyword returns Lock icon`() {
        assertEquals(Icons.Filled.Lock, facilityIconFor("secure locker"))
    }

    @Test
    fun `luggage keyword returns Work icon`() {
        assertEquals(Icons.Filled.Work, facilityIconFor("luggage storage"))
    }

    @Test
    fun `reception keyword returns SupportAgent icon`() {
        assertEquals(Icons.Filled.SupportAgent, facilityIconFor("24h reception"))
    }

    @Test
    fun `medical keyword returns LocalHospital icon`() {
        assertEquals(Icons.Filled.LocalHospital, facilityIconFor("medical assistance"))
    }

    @Test
    fun `air conditioning keyword returns AcUnit icon`() {
        assertEquals(Icons.Filled.AcUnit, facilityIconFor("air conditioning"))
    }

    @Test
    fun `unknown facility returns CheckCircle default icon`() {
        assertEquals(Icons.Filled.CheckCircle, facilityIconFor("mystery amenity xyz"))
    }

    @Test
    fun `icon matching is case insensitive`() {
        assertEquals(facilityIconFor("WIFI"), facilityIconFor("wifi"))
    }

    // endregion

    // region facilityDescriptionRes

    @Test
    fun `wifi keyword returns wifi description resource`() {
        assertEquals(R.string.facility_desc_wifi, facilityDescriptionRes("Free WiFi"))
    }

    @Test
    fun `pool keyword returns pool description resource`() {
        assertEquals(R.string.facility_desc_pool, facilityDescriptionRes("swimming pool"))
    }

    @Test
    fun `gym keyword returns gym description resource`() {
        assertEquals(R.string.facility_desc_gym, facilityDescriptionRes("Gym"))
    }

    @Test
    fun `kitchen keyword returns kitchen description resource`() {
        assertEquals(R.string.facility_desc_kitchen, facilityDescriptionRes("communal kitchen"))
    }

    @Test
    fun `laundry keyword returns laundry description resource`() {
        assertEquals(R.string.facility_desc_laundry, facilityDescriptionRes("Laundry"))
    }

    @Test
    fun `unknown facility returns default description resource`() {
        assertEquals(R.string.facility_desc_default, facilityDescriptionRes("mystery amenity xyz"))
    }

    @Test
    fun `description matching is case insensitive`() {
        assertEquals(facilityDescriptionRes("WIFI"), facilityDescriptionRes("wifi"))
    }

    // endregion
}
