package br.com.texsistemas.transita.presentation.viewmodel.home

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import br.com.texsistemas.transita.domain.usecase.GetPontoOnibusUseCase
import br.com.texsistemas.transita.domain.usecase.GetUsuarioLocalizacaoUseCase
import br.com.texsistemas.transita.domain.usecase.LoginUseCase
import br.com.texsistemas.transita.presentation.ui.common.bitmapDescriptorFromVector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class HomeViewModel(
    private val applicationContext: Context
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val loginUseCase: LoginUseCase by inject()
    private val getUsuarioLocalizacaoUseCase: GetUsuarioLocalizacaoUseCase by inject()
    private val getPontoOnibusUseCase: GetPontoOnibusUseCase by inject()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnFetchUsuarioLocalizacao -> {
                viewModelScope.launch {
                    fetchUsuarioLocalizacao()
                    onEvent(HomeUiEvent.OnFetchPontosOnibusProximo)
                }
            }

            HomeUiEvent.OnFetchPontosOnibusProximo -> fetchPontosOnibusProximo()
            is HomeUiEvent.OnFetchDadosMapa -> fetchDadosMapa()
            is HomeUiEvent.AtualizarImagemLocal -> atualizarImagemLocal(
                event.pontoId,
                event.imagemLocal
            )

            is HomeUiEvent.OnZoomChanged -> changeZoom(zoom = event.zoom)
            is HomeUiEvent.OnTipoPontoChange -> changeTipoPontoSelecionado(tipo = event.tipo)
        }
    }

    private fun changeTipoPontoSelecionado(tipo: String) {
        _uiState.update { it.copy(tipoPontoSelecionado = tipo) }
    }

    private fun changeZoom(zoom: Float) {
        val novoRaio = calcularRaioProporcional(zoom)
        _uiState.update { it.copy(mapZoom = zoom, raio = novoRaio) }
        fetchPontosOnibusProximo()
    }

    private suspend fun fetchUsuarioLocalizacao() {
        val usuarioPosicao = getUsuarioLocalizacaoUseCase.solicitarLocalizacao()
        _uiState.update { currentUiState ->
            currentUiState.copy(usuarioPosicao = usuarioPosicao)
        }
    }

    private fun fetchDadosMapa() {
        if (_uiState.value.userMapIcon != null && _uiState.value.pinMapIcon != null) {
            _uiState.update { it.copy(isDadosMapaCarregado = true) }
            return // Já carregados
        }

        viewModelScope.launch {
            try {
                val userIcon =
                    bitmapDescriptorFromVector(
                        context = applicationContext,
                        res = R.drawable.ic_user_location
                    )
                val pinIcon = bitmapDescriptorFromVector(
                    context = applicationContext,
                    res = R.drawable.img_pin,
                    width = 120,
                    height = 135
                )

                _uiState.update { currentState ->
                    if (userIcon != null && pinIcon != null) {
                        currentState.copy(
                            isDadosMapaCarregado = true,
                            isCarregandoMapa = true,
                            dadosMapaErro = null,
                            userMapIcon = userIcon,
                            pinMapIcon = pinIcon,
                        )
                    } else {
                        // Um ou ambos os ícones falharam ao carregar
                        currentState.copy(
                            isDadosMapaCarregado = false,
                            isCarregandoMapa = true,
                            dadosMapaErro = "Falha ao carregar ícones do mapa.",
                            userMapIcon = null,
                            pinMapIcon = null,
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isDadosMapaCarregado = false,
                        isCarregandoMapa = true,
                        dadosMapaErro = "Erro ao preparar recursos do mapa: ${e.message}"
                    )
                }
            }
        }
    }

    private fun fetchPontosOnibusProximo() {
        viewModelScope.launch {
            val usuarioPosicaoData: UsuarioPosicao? = _uiState.value.usuarioPosicao
            val raioAtual = _uiState.value.raio

            // Centro da cidade e limite de distância
            val centroLatitude = -7.988681
            val centroLongitude = -38.297155
            val limiteDistanciaKm = 4.0

            Log.d("HomeViewModel", "Buscando pontos. Raio: $raioAtual km")
            Log.d(
                "HomeViewModel",
                "Dados de Posição do Usuário (UsuarioPosicao): $usuarioPosicaoData"
            )

            if (usuarioPosicaoData != null) {
                // Converter UsuarioPosicao para android.location.Location
                val posicaoUsuarioLocation = Location("posicaoUsuario").apply {
                    latitude = usuarioPosicaoData.latitude
                    longitude = usuarioPosicaoData.longitude
                }

                // Verificar se a posição do usuário está dentro do limite de distância
                val centroLocation = Location("centroCidade").apply {
                    latitude = centroLatitude
                    longitude = centroLongitude
                }

                val distanciaKm = posicaoUsuarioLocation.distanceTo(centroLocation) / 1000.0
                val raioBusca = if (distanciaKm > limiteDistanciaKm) distanciaKm else raioAtual

                try {
                    // buscarPontosOnibusProximos ainda usa latitude/longitude como parâmetros
                    val pontosOnibusDoRaio = getPontoOnibusUseCase.buscarPontosOnibusProximos(
                        latitude = posicaoUsuarioLocation.latitude,
                        longitude = posicaoUsuarioLocation.longitude,
                        raio = raioBusca
                    )

                    // Ordenar os pontos pela distância usando Location.distanceTo()
                    val pontosOnibusOrdenados = pontosOnibusDoRaio.sortedBy { pontoOnibus ->
                        val pontoOnibusLocation = Location("pontoOnibus").apply {
                            latitude = pontoOnibus.latitude
                            longitude = pontoOnibus.longitude
                        }
                        // distanceTo retorna a distância em metros
                        posicaoUsuarioLocation.distanceTo(pontoOnibusLocation)
                    }

                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            pontosOnibus = pontosOnibusOrdenados
                        )
                    }
                } catch (e: Exception) {
                    Log.e(
                        "HomeViewModel",
                        "Erro ao buscar ou ordenar pontos de ônibus: ${e.message}",
                        e
                    )
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            pontosOnibus = emptyList()
                        )
                    }
                }
            } else {
                Log.w("HomeViewModel", "Dados de Posição do Usuário (UsuarioPosicao) são nulos.")
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        pontosOnibus = emptyList()
                    )
                }
            }
        }
    }

    /**
     * Calcula o raio de busca com base no nível de zoom do mapa.
     * A lógica é: quanto menor o zoom (mais afastado), maior o raio.
     */
    private fun calcularRaioProporcional(zoom: Float): Double {
        // Define um intervalo de zoom para o cálculo (ex: de 10 a 18)
        val clampedZoom = zoom.coerceIn(10f, 18f)

        // Parâmetros de calibração
        val baseZoom = 15f   // Zoom de referência
        val baseRaio = 0.5   // Raio de 500m no zoom de referência
        val zoomFactor = 0.5 // Aumenta 500m de raio para cada nível de zoom diminuído

        // O raio aumenta quando o zoom diminui
        val novoRaio = baseRaio + (baseZoom - clampedZoom) * zoomFactor

        // Garante um raio mínimo (ex: 500m) e máximo (ex: 5km)
        return novoRaio.coerceIn(0.5, 5.0)
    }

    fun getPontoOnibusById(pontoOnibusId: String): PontoOnibus? {
        return _uiState.value.pontosOnibus?.find { it.id == pontoOnibusId }
    }

    private fun atualizarImagemLocal(pontoId: String, imagemLocal: String) {
        viewModelScope.launch {
            try {
                val pontoOnibusDao: br.com.texsistemas.transita.data.local.dao.PontoOnibusDao by inject()
                pontoOnibusDao.updateImagemLocal(pontoId, imagemLocal)
                // Atualiza o estado local para refletir a mudança imediatamente na UI
                _uiState.update { currentUiState ->
                    val pontosAtualizados = currentUiState.pontosOnibus?.map {
                        if (it.id == pontoId) it.copy(imagemLocal = imagemLocal) else it
                    }
                    currentUiState.copy(pontosOnibus = pontosAtualizados)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro ao atualizar imagemLocal: ${e.message}", e)
            }
        }
    }
}