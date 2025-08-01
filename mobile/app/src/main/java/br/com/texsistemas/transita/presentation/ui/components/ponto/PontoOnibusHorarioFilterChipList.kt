package br.com.texsistemas.transita.presentation.ui.components.ponto

import java.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.core.common.utils.horarioParaMinutos
import br.com.texsistemas.transita.domain.enums.HorarioStatus
import br.com.texsistemas.transita.domain.model.Horario
import java.util.UUID

@Composable
fun PontoOnibusHorarioFilterChipList(
    modifier: Modifier = Modifier,
    pontoId: String,
    horarios: List<Horario>,
    onSelectedHorarioChanged: (Horario) -> Unit
) {
    val agoraCalendar = remember { Calendar.getInstance() }
    val minutosAtuais =
        agoraCalendar.get(Calendar.HOUR_OF_DAY) * 60 + agoraCalendar.get(Calendar.MINUTE)

    val horariosOrdenadosEComStatus by remember(horarios, minutosAtuais) {
        derivedStateOf {
            val comStatus = horarios.map { horario ->
                val minutosHorario = horarioParaMinutos(horario.horarioFormatado)
                val status =
                    if (minutosHorario < minutosAtuais) HorarioStatus.PASSADO else HorarioStatus.ATUAL_OU_FUTURO
                Pair(horario, status)
            }

            val passados = comStatus
                .filter { it.second == HorarioStatus.PASSADO }
                .sortedBy { horarioParaMinutos(it.first.horarioFormatado) } // do mais antigo ao mais recente

            val futuros = comStatus
                .filter { it.second == HorarioStatus.ATUAL_OU_FUTURO }
                .sortedBy { horarioParaMinutos(it.first.horarioFormatado) }

            passados + futuros // Passados à esquerda, futuros à direita
        }
    }

    val horarioInicialSelecionado by remember(horariosOrdenadosEComStatus) {
        derivedStateOf {
            horariosOrdenadosEComStatus.find { it.second == HorarioStatus.ATUAL_OU_FUTURO }?.first
        }
    }

    var selectedHorarioId by remember { mutableStateOf(horarioInicialSelecionado?.id.orEmpty()) }

    LaunchedEffect(horarioInicialSelecionado) {
        horarioInicialSelecionado?.id?.let {
            if (it.isNotEmpty() && selectedHorarioId != it) {
                selectedHorarioId = it
            }
        }
    }

    LaunchedEffect(key1 = selectedHorarioId, key2 = horarios) {
        val selectedHorarioOrNull = horarios.find { it.id == selectedHorarioId }
        selectedHorarioOrNull?.let {
            onSelectedHorarioChanged(it)
        }
    }

    val listState = rememberLazyListState()

    LaunchedEffect(selectedHorarioId, horariosOrdenadosEComStatus) {
        val selectedIndex =
            horariosOrdenadosEComStatus.indexOfFirst { it.first.id == selectedHorarioId }
        if (selectedIndex != -1) {
            listState.animateScrollToItem(index = selectedIndex)
        }
    }

    LazyRow(
        modifier = modifier,
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = horariosOrdenadosEComStatus,
            key = { "${pontoId}_${it.first.tipoPonto}_${it.first.id}" }
        ) { (horario, status) ->
            PontoOnibusHorarioFilterChip(
                horario = horario,
                isSelected = horario.id == selectedHorarioId,
                horarioStatus = status,
                onClick = { isSelected ->
                    if (isSelected) {
                        selectedHorarioId = horario.id
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoOnibusHorarioFilterChipListPreview() {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    val mockHorarios = mutableListOf<Horario>()
    for (i in 0..5) {
        calendar.set(Calendar.HOUR_OF_DAY, 9 + i * 2)
        calendar.set(Calendar.MINUTE, 0)
        mockHorarios.add(
            Horario(
                id = "id$i",
                horario = sdf.format(calendar.time),
                tipoPonto = "EMBARQUE"
            )
        )
    }

    calendar.set(Calendar.HOUR_OF_DAY, 7)
    calendar.set(Calendar.MINUTE, 0)
    mockHorarios.add(
        0,
        Horario(
            id = "idPassado",
            horario = sdf.format(calendar.time),
            tipoPonto = "EMBARQUE"
        )
    )

    PontoOnibusHorarioFilterChipList(
        modifier = Modifier.fillMaxWidth(),
        pontoId = UUID.randomUUID().toString(),
        horarios = mockHorarios,
        onSelectedHorarioChanged = {}
    )
}
