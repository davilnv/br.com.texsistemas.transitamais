package br.com.texsistemas.transita.presentation.ui.components.global.map

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import br.com.texsistemas.transita.presentation.ui.common.bitmapDescriptorFromVector
import br.com.texsistemas.transita.presentation.ui.components.global.map.google.GoogleMapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    zoom: Float? = null,
    center: UsuarioPosicao? = null,
    pontosOnibus: List<PontoOnibus> = emptyList(),
    userIcon: BitmapDescriptor?,
    pinIcon: BitmapDescriptor?,
    onZoomChanged: ((Float) -> Unit)? = null
) {
    GoogleMapView(
        modifier = modifier,
        zoom = zoom?.toFloat() ?: 15f,
        center = center?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(-7.988681, -38.297155),
        markers = pontosOnibus.mapNotNull { it.marcadorMapa },
        userIcon = userIcon,
        pinIcon = pinIcon,
        onZoomChanged
    )
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    zoom: Float? = null,
    center: UsuarioPosicao? = null,
    pontosOnibus: List<PontoOnibus> = emptyList(),
    context: Context,
    hasUserIcon: Boolean = true,
    @DrawableRes userIconRes: Int = R.drawable.ic_user_location,
    @DrawableRes pinIconRes: Int = R.drawable.img_pin,
) {
    val userIcon =
        bitmapDescriptorFromVector(
            context = context,
            res = userIconRes
        )
    val pinIcon = bitmapDescriptorFromVector(
        context = context,
        res = pinIconRes,
        width = 120,
        height = 135
    )

    MapView(
        modifier = modifier,
        zoom = zoom,
        center = center,
        pontosOnibus = pontosOnibus,
        userIcon = if (hasUserIcon) userIcon else null,
        pinIcon = pinIcon
    )
}