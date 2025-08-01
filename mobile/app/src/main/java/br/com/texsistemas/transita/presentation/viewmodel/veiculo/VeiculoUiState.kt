package br.com.texsistemas.transita.presentation.viewmodel.veiculo

import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.Veiculo
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

data class VeiculoUiState(
    val isLoading: Boolean = true,
    val veiculo: Veiculo? = null,
    val pontosOnibusNaRota: List<PontoOnibus> = emptyList(),
    val rotaCoordinates: List<LatLng> = emptyList(),
    val vehicleIcon: BitmapDescriptor? = null,
    val pinMapIcon: BitmapDescriptor? = null,
    val errorMessage: String? = null
)