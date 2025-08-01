package br.com.texsistemas.transita.presentation.viewmodel.veiculo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.data.repository.PontoOnibusRepository
import br.com.texsistemas.transita.domain.usecase.GetVeiculoUseCase
import br.com.texsistemas.transita.presentation.ui.common.bitmapDescriptorFromVector
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VeiculoViewModel(
    private val applicationContext: Context
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(VeiculoUiState())
    val uiState: StateFlow<VeiculoUiState> = _uiState.asStateFlow()

    private val getVeiculoUseCase: GetVeiculoUseCase by inject()
    private val pontoOnibusRepository: PontoOnibusRepository by inject()

    init {
        loadMapIcons()
    }

    fun onEvent(event: VeiculoUiEvent) {
        when (event) {
            is VeiculoUiEvent.OnFetchVeiculoDetalhes -> fetchVehicleDetails(event.vehicleId)
        }
    }

    private fun loadMapIcons() {
        viewModelScope.launch {
            val vehicleIcon =
                bitmapDescriptorFromVector(applicationContext, R.drawable.bus_icon, 80, 80)
            val pinIcon =
                bitmapDescriptorFromVector(applicationContext, R.drawable.ic_map_pin, 80, 80)
            _uiState.update { it.copy(vehicleIcon = vehicleIcon, pinMapIcon = pinIcon) }
        }
    }

    private fun fetchVehicleDetails(vehicleId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // 1. Buscar os detalhes do veículo
                val veiculo = getVeiculoUseCase.buscarVeiculoPorId(vehicleId)

                if (veiculo != null) {
                    // 2. Extrair os IDs dos pontos da rota e a ordem
                    val rotaComOrdem = veiculo.rotaVeiculoPonto
                    val pontoIds = rotaComOrdem.map { it.pontoId }

                    // 3. Buscar os objetos PontoOnibus a partir dos IDs
                    val pontosOnibus = pontoOnibusRepository.getPontosOnibusByIds(pontoIds)

                    // 4. Mapear e ordenar os pontos para criar as coordenadas da rota (Polyline)
                    val pontosOnibusMap = pontosOnibus.associateBy { it.id }
                    val rotaOrdenada = rotaComOrdem
                        .sortedBy { it.ordem }
                        .mapNotNull { rotaPonto -> pontosOnibusMap[rotaPonto.pontoId] }

                    val routeCoordinates = rotaOrdenada.map { ponto ->
                        LatLng(ponto.latitude, ponto.longitude)
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            veiculo = veiculo,
                            pontosOnibusNaRota = rotaOrdenada,
                            rotaCoordinates = routeCoordinates,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Veículo não encontrado."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}