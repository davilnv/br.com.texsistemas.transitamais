package br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus

import androidx.lifecycle.ViewModel
import br.com.texsistemas.transita.domain.model.Horario
import br.com.texsistemas.transita.domain.model.PontoOnibus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class PontoOnibusViewModel : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(PontoOnibusUiState())
    val uiState: StateFlow<PontoOnibusUiState> = _uiState.asStateFlow()

    fun onEvent(event: PontoOnibusUiEvent) {
        when (event) {
            is PontoOnibusUiEvent.OnFetchPontoOnibus -> fetchPontoOnibus()
            is PontoOnibusUiEvent.OnTipoPontoChange -> setTipoPontOnibusHorarios(tipoPonto = event.novoTipo)
            is PontoOnibusUiEvent.OnHorarioPontoOnibusSelecionado -> setHorarioPontoOnibusSelecionado(horario = event.horario)
        }
    }

    fun fetchPontoOnibus() {

    }

    fun setTipoPontOnibusHorarios(tipoPonto: String) {
//        _uiState.value = _uiState.value.copy(tipoPontoSelecionado = tipoPonto)
        _uiState.update { currentUiState ->
            currentUiState.copy(tipoPontoSelecionado = tipoPonto)
        }
    }

    fun setPontoOnibusSelecionado(ponto: PontoOnibus) {
        _uiState.value = _uiState.value.copy(pontoOnibus = ponto)
    }

    fun setHorarioPontoOnibusSelecionado(horario: Horario) {
        // Atualiza o próxio/atual horário selecionado
        _uiState.update { currentUiState ->
            val pontoAtualizado = currentUiState.pontoOnibus?.copy(proximoHorario = horario)
            currentUiState.copy(
//                horarioPontoOnibusSelecionado = horario,
                pontoOnibus = pontoAtualizado
            )
        }
    }
}