package br.com.texsistemas.transita.presentation.ui.components.global.map.google

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.texsistemas.transita.domain.model.MarcadorMapa
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Offset
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    zoom: Float = 15f,
    center: LatLng = LatLng(7.988681, -38.297155),
    markers: List<MarcadorMapa> = emptyList(),
    userIcon: BitmapDescriptor?,
    pinIcon: BitmapDescriptor?,
    onZoomChanged: ((Float) -> Unit)? = null
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(center, zoom)
    }

    val userMarkerState = remember { MarkerState(position = center) }

    LaunchedEffect(center, zoom) {
        userMarkerState.position = center // Atualiza o marcador
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(center, zoom)
        )
    }

    // Listener para o final do movimento da câmera
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onZoomChanged?.invoke(cameraPositionState.position.zoom)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        // Só desenha o marker se o ícone estiver carregado
        userIcon?.let { icon ->
            Marker(
                state = userMarkerState,
                title = "Você está aqui",
                icon = icon,
                anchor = Offset(0.5f, 0.5f)
            )
        }

        // Mostra os pontos com ícone de pin (se carregado)
        pinIcon?.let { icon ->
            markers.forEach { mapMarker ->
                val markerPosition = LatLng(mapMarker.latitude, mapMarker.longitude)
                val markerState = remember(markerPosition) {
                    MarkerState(position = markerPosition)
                }

                Marker(
                    state = markerState,
                    title = mapMarker.titulo,
                    icon = icon
                )
            }
        }
    }
}