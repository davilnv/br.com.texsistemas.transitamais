package br.com.texsistemas.transita.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.presentation.ui.common.mock.pontoOnibusMock
import br.com.texsistemas.transita.presentation.ui.components.global.TransitaButton
import br.com.texsistemas.transita.presentation.ui.components.ponto.PontoBarraClassificacao
import br.com.texsistemas.transita.presentation.ui.components.ponto.PontoOnibusDetalhesInfo
import br.com.texsistemas.transita.presentation.ui.components.ponto.PontoOnibusHorarios
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus.PontoOnibusUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus.PontoOnibusUiState
import coil.compose.AsyncImage

@Composable
fun DetalhesPontoOnibusScreen(
    modifier: Modifier = Modifier,
    uiState: PontoOnibusUiState,
    onEvent: (PontoOnibusUiEvent) -> Unit,
    pontoOnibus: PontoOnibus,
    onNavigateBack: () -> Unit,
    onNavigateToLocalizacao: (PontoOnibus, String?) -> Unit,
    onNavigateToVeiculo: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (pontoOnibus.imagemLocal != null) {
            AsyncImage(
                model = java.io.File(pontoOnibus.imagemLocal),
                contentDescription = stringResource(R.string.content_description_capa_ponto_onibus),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                placeholder = painterResource(R.drawable.img_sem_imagem),
                error = painterResource(R.drawable.img_sem_imagem)
            )
        } else {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                painter = painterResource(R.drawable.img_sem_imagem),
                contentDescription = stringResource(R.string.content_description_capa_ponto_onibus),
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 32.dp, top = 40.dp, end = 32.dp, bottom = 32.dp),
            ) {
                Column {
                    Text(
                        text = pontoOnibus.titulo,
                        color = Gray600,
                        style = Typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Text(
                        text = pontoOnibus.descricao,
                        color = Gray500,
                        style = Typography.bodyLarge,
                        textAlign = TextAlign.Justify
                    )
                }

                Spacer(modifier = Modifier.padding(top = 16.dp))
                PontoBarraClassificacao(
                    rating = pontoOnibus.avaliacao
                )

                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    PontoOnibusDetalhesInfo(
                        ponto = pontoOnibus
                    )


                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    PontoOnibusHorarios(
                        ponto = pontoOnibus,
                        uiState = uiState,
                        onEvent = onEvent
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                ) {
                    TransitaButton(
                        iconRes = R.drawable.ic_map_pin,
                        onClick = {
                            onNavigateToLocalizacao(
                                pontoOnibus,
                                uiState.pontoOnibus?.proximoHorario?.horarioFormatado
                            )
                        }
                    )

                    Spacer(
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    TransitaButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.label_button_acompanhar_veiculo),
                        onClick = {
                            onNavigateToVeiculo(
                                "1" // todo: aqui ta fixo, mas deve ser o id do veículo associado ao ponto de ônibus/horário selecionado
                            )
                        }
                    )
                }

            }
        }

        TransitaButton(
            modifier = Modifier
                .zIndex(1f)
                .padding(16.dp),
            iconRes = R.drawable.ic_arrow_left,
            onClick = onNavigateBack
        )
    }


}

@Preview
@Composable
private fun BusStopDetailsPreview() {
    DetalhesPontoOnibusScreen(
        pontoOnibus = pontoOnibusMock,
        uiState = PontoOnibusUiState(),
        onEvent = {},
        onNavigateBack = {},
        onNavigateToLocalizacao = { _, _ -> },
        onNavigateToVeiculo = {}
    )

}
