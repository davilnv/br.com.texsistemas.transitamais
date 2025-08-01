package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.presentation.ui.common.mock.pontoOnibusMock
import br.com.texsistemas.transita.presentation.ui.components.global.CustomSwitchComTexto
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus.PontoOnibusUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus.PontoOnibusUiState

@Composable
fun PontoOnibusHorarios(
    modifier: Modifier = Modifier,
    ponto: PontoOnibus,
    uiState: PontoOnibusUiState,
    onEvent: (PontoOnibusUiEvent) -> Unit,
) {

    // Filtro de horários de acordo com o tipo de ponto selecionado EMBARQUE/DESEMBARQUE
    val horariosFiltrados = remember(uiState.tipoPontoSelecionado, ponto.horarios) {
        ponto.horarios.filter {
            it.tipoPonto.equals(
                uiState.tipoPontoSelecionado,
                ignoreCase = true
            )
        }
        .distinctBy { "${it.id}_${it.tipoPonto}" }
    }

    val pontoComHorariosFiltrados = ponto.copy(horarios = horariosFiltrados)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Horário/Rota:", style = Typography.headlineSmall, color = Gray500)

            CustomSwitchComTexto(
                checked = uiState.tipoPontoSelecionado == "DESEMBARQUE",
                onCheckedChange = { isChecked ->
                    val novoTipo = if (isChecked) "DESEMBARQUE" else "EMBARQUE"
                    onEvent(PontoOnibusUiEvent.OnTipoPontoChange(novoTipo))
                },
                textoLigado = "DESEMBARQUE",
                textoDesligado = "EMBARQUE"
            )
        }

        Text(
            text = "Selecione um horário para ver rota e localização do ônibus",
            style = Typography.bodyMedium,
            color = Gray500
        )

        if (!pontoComHorariosFiltrados.horarios.isEmpty()) {
            PontoOnibusHorarioFilterChipList(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(top = 24.dp)
                    .align(Alignment.Start),
                pontoId = pontoComHorariosFiltrados.id,
                horarios = pontoComHorariosFiltrados.horarios,
                onSelectedHorarioChanged = {
                    onEvent(PontoOnibusUiEvent.OnHorarioPontoOnibusSelecionado(it))
                }
//                onSelectedCategoryChanged = { horario ->
//                    onEvent(HomeUiEvent.OnFetchMarkets(categoryId = selectedCategory.id))
//                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoOnibusHorariosPreview() {
    PontoOnibusHorarios(
        modifier = Modifier.fillMaxWidth(),
        ponto = pontoOnibusMock,
        uiState = PontoOnibusUiState(),
        onEvent = {}
    )
}