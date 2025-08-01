package br.com.texsistemas.transita.presentation.viewmodel.home

sealed class HomeUiEvent {
    data object OnFetchDadosMapa : HomeUiEvent()
    data object OnFetchPontosOnibusProximo : HomeUiEvent()
    data object OnFetchUsuarioLocalizacao : HomeUiEvent()
    data class AtualizarImagemLocal(val pontoId: String, val imagemLocal: String) : HomeUiEvent()
    data class OnZoomChanged(val zoom: Float) : HomeUiEvent()
    data class OnTipoPontoChange(val tipo: String) : HomeUiEvent()
}