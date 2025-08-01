package br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus

import br.com.texsistemas.transita.domain.model.PontoOnibus

data class PontoOnibusUiState (
    val pontoOnibus: PontoOnibus? = null,
    val tipoPontoSelecionado: String = "EMBARQUE"
)