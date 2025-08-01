package br.com.texsistemas.transita.presentation.ui.components.global.map


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.presentation.ui.theme.GreenBase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapaComRotaEVeiculo(
    modifier: Modifier = Modifier,
    vehiclePosition: LatLng,
    routeCoordinates: List<LatLng>,
    pontosOnibus: List<PontoOnibus>,
    vehicleIcon: BitmapDescriptor?,
    pinIcon: BitmapDescriptor?
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(vehiclePosition, 15f)
    }

    LaunchedEffect(routeCoordinates) {
        if (routeCoordinates.isNotEmpty()) {
            val boundsBuilder = LatLngBounds.builder()
            routeCoordinates.forEach { boundsBuilder.include(it) }
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100),
                1000
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        Polyline(
            points = routeCoordinates,
            color = GreenBase,
            width = 15f
        )

        vehicleIcon?.let {
            Marker(
                state = MarkerState(position = vehiclePosition),
                title = "Localização do Veículo",
                icon = it,
                zIndex = 1f
            )
        }

        pinIcon?.let {
            pontosOnibus.forEach { ponto ->
                Marker(
                    state = MarkerState(position = LatLng(ponto.latitude, ponto.longitude)),
                    title = ponto.titulo,
                    icon = it
                )
            }
        }
    }
}