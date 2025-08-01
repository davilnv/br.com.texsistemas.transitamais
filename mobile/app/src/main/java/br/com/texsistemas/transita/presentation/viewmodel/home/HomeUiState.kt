package br.com.texsistemas.transita.presentation.viewmodel.home

import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.MarcadorMapa
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import com.google.android.gms.maps.model.BitmapDescriptor


data class HomeUiState(
    val isCarregandoMapa: Boolean = false,
    val isDadosMapaCarregado: Boolean = false,
    val dadosMapaErro: String? = null,
    val userMapIcon: BitmapDescriptor? = null,
    val pinMapIcon: BitmapDescriptor? = null,
    val pontosOnibus: List<PontoOnibus>? = emptyList(),
    val localizacaoPontosOnibus: List<MarcadorMapa>? = null,
    val usuarioPosicao: UsuarioPosicao? = null,
    val mapZoom: Float = 15f,
    val raio: Double = 0.5,
    val tipoPontoSelecionado: String = "AMBOS"
)