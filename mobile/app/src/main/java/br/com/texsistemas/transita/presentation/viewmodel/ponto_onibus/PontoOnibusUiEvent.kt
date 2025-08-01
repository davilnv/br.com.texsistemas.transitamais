package br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus

import br.com.texsistemas.transita.domain.model.Horario

sealed class PontoOnibusUiEvent {
    data object OnFetchPontoOnibus : PontoOnibusUiEvent()
    data class OnTipoPontoChange(val novoTipo: String) : PontoOnibusUiEvent()
    data class OnHorarioPontoOnibusSelecionado(val horario: Horario) : PontoOnibusUiEvent()
}