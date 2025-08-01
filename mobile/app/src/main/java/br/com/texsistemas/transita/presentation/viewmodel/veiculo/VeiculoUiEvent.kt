package br.com.texsistemas.transita.presentation.viewmodel.veiculo

sealed class VeiculoUiEvent {
    data class OnFetchVeiculoDetalhes(val vehicleId: String) : VeiculoUiEvent()
}