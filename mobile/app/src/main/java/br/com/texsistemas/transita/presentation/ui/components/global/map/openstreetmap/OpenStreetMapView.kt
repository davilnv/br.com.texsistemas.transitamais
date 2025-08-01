package br.com.texsistemas.transita.presentation.ui.components.global.map.openstreetmap

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.ui.Modifier
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.MarcadorMapa
import org.osmdroid.util.GeoPoint

@Composable
fun OpenStreetMapView(
    modifier: Modifier = Modifier,
    context: Context,
    zoom: Double = 15.0,
    center: Pair<Double, Double> = Pair(48.8583, 2.2944),
    markers: List<MarcadorMapa>
) {
    // Inicializa a configuração do osmdroid
    Configuration.getInstance()
        .load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))

    AndroidView(factory = {
        MapView(it).apply {
            // Set the map view settings
            setMultiTouchControls(true)
            controller.setZoom(zoom)
            controller.setCenter(GeoPoint(center.first, center.second))

            // Show the user location Marker
            val userLocationMarker = Marker(this)
            userLocationMarker.position =
                GeoPoint(center.first, center.second)
            userLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            userLocationMarker.icon = context.getDrawable(R.drawable.ic_user_location)
            overlays.add(userLocationMarker)

            // Convert the MapMarker to Marker and add to the map
            markers.forEach { mapMarker ->
                val marker = Marker(this)
                marker.position =
                    GeoPoint(mapMarker.latitude, mapMarker.longitude)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.icon = context.getDrawable(R.drawable.ic_map_pin)
                marker.title = mapMarker.titulo
                overlays.add(marker)
            }
        }
    })
}