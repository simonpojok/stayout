package com.example.stayout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun PropertyMapView(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
    hasLocationPermission: Boolean = false,
) {
    if (LocalInspectionMode.current) {
        Box(
            modifier =
                modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Map,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp),
            )
        }
        return
    }

    val context = LocalContext.current
    val hotelPoint = remember(latitude, longitude) { GeoPoint(latitude, longitude) }

    val mapView =
        remember {
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                setBuiltInZoomControls(false)
                isHorizontalMapRepetitionEnabled = false
                isVerticalMapRepetitionEnabled = false
                controller.setZoom(14.0)
                controller.setCenter(hotelPoint)
                overlays.add(
                    Marker(this).also {
                        it.position = hotelPoint
                        it.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    },
                )
            }
        }

    // Add or remove user-location overlay when permission changes
    DisposableEffect(hasLocationPermission) {
        var locationOverlay: MyLocationNewOverlay? = null
        if (hasLocationPermission) {
            locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
            locationOverlay.enableMyLocation()
            mapView.overlays.add(locationOverlay)

            locationOverlay.runOnFirstFix {
                val userPoint = locationOverlay.myLocation
                if (userPoint != null) {
                    mapView.post {
                        val bounds = BoundingBox.fromGeoPoints(listOf(hotelPoint, userPoint))
                        mapView.zoomToBoundingBox(bounds, true, 100)
                    }
                }
            }
        }
        onDispose {
            locationOverlay?.disableMyLocation()
            locationOverlay?.let { mapView.overlays.remove(it) }
        }
    }

    DisposableEffect(Unit) {
        mapView.onResume()
        onDispose { mapView.onPause() }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.clip(MaterialTheme.shapes.medium),
    )
}
