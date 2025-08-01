package br.com.texsistemas.transita.presentation.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import br.com.texsistemas.transita.presentation.ui.common.mock.pontoOnibusMock
import br.com.texsistemas.transita.presentation.ui.components.global.TransitaButton
import br.com.texsistemas.transita.presentation.ui.components.global.map.MapaLayoutScreen
import br.com.texsistemas.transita.presentation.ui.components.ponto.LocalizacaoPontoCard

@Composable
fun LocalizacaoPontoScreen(
    modifier: Modifier = Modifier,
    pontoOnibus: PontoOnibus,
    proximoHorario: String,
    onNavigateBack: () -> Unit
) {
    MapaLayoutScreen(
        modifier = modifier.fillMaxSize(),
        useUserIcon = false,
        mapCenter = UsuarioPosicao(pontoOnibus.latitude, pontoOnibus.longitude),
        pontosOnibus = listOf(pontoOnibus),
        mapZoom = 16.0f,
        sheetPeekHeight = 220.dp,

        overlayContent = {
            Box(modifier = Modifier.padding(16.dp)) {
                TransitaButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    iconRes = R.drawable.ic_arrow_left,
                    onClick = onNavigateBack
                )
            }
        },

        useSheet = false,
        sheetContent = {
            LocalizacaoPontoCard(
                nome = pontoOnibus.titulo,
                endereco = pontoOnibus.endereco.toString(),
                proximoHorario = proximoHorario,
                classificacao = pontoOnibus.avaliacao
            )
        }
    )
}

@Preview
@Composable
private fun LocalizacaoPontoScreenPreview() {
    LocalizacaoPontoScreen(
        pontoOnibus = pontoOnibusMock,
        proximoHorario = "10:30",
        onNavigateBack = {}
    )
}