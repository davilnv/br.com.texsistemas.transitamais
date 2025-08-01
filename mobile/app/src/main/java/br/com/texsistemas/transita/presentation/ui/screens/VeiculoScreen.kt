package br.com.texsistemas.transita.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import br.com.texsistemas.transita.presentation.ui.components.global.TransitaButton
import br.com.texsistemas.transita.presentation.ui.components.global.map.MapaComRotaEVeiculo
import br.com.texsistemas.transita.presentation.ui.components.global.map.MapaLayoutScreen
import br.com.texsistemas.transita.presentation.viewmodel.veiculo.VeiculoUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.veiculo.VeiculoUiState
import com.google.android.gms.maps.model.LatLng

@Composable
fun VeiculoScreen(
    modifier: Modifier = Modifier,
    veiculoId: String,
    uiState: VeiculoUiState,
    onEvent: (VeiculoUiEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(key1 = veiculoId) {
        onEvent(VeiculoUiEvent.OnFetchVeiculoDetalhes(veiculoId))
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.errorMessage != null) {
            Text(
                text = "Erro: ${uiState.errorMessage}",
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (uiState.veiculo != null) {
            val veiculo = uiState.veiculo
            val vehiclePosition =
                LatLng(veiculo.informacoesVeiculo.latitude, veiculo.informacoesVeiculo.longitude)

            MapaLayoutScreen(
                mapCenter = UsuarioPosicao(
                    veiculo.informacoesVeiculo.latitude,
                    veiculo.informacoesVeiculo.longitude
                ),
                pontosOnibus = uiState.pontosOnibusNaRota,
                mapZoom = 14f,
                useSheet = false,
                overlayContent = {
                    TransitaButton(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp),
                        iconRes = R.drawable.ic_arrow_left,
                        onClick = onNavigateBack
                    )

                    MapaComRotaEVeiculo(
                        modifier = Modifier.fillMaxSize(),
                        vehiclePosition = vehiclePosition,
                        routeCoordinates = uiState.rotaCoordinates,
                        pontosOnibus = uiState.pontosOnibusNaRota,
                        vehicleIcon = uiState.vehicleIcon,
                        pinIcon = uiState.pinMapIcon
                    )
                },
                sheetContent = {
                    //TODO Aqui poderia ir um card com informações do veículo, se desejado.
                }
            )
        }
    }
}

@Preview
@Composable
private fun VeiculoScreenPreview() {
    VeiculoScreen(
        veiculoId = "1",
        uiState = VeiculoUiState(),
        onEvent = {},
        onNavigateBack = {}
    )
}