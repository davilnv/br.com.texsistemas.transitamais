package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.domain.enums.HorarioStatus
import br.com.texsistemas.transita.domain.model.Horario
import br.com.texsistemas.transita.presentation.ui.common.mock.pontoOnibusMock
import br.com.texsistemas.transita.presentation.ui.theme.Gray300
import br.com.texsistemas.transita.presentation.ui.theme.Gray400
import br.com.texsistemas.transita.presentation.ui.theme.GreenBase
import br.com.texsistemas.transita.presentation.ui.theme.LightRed
import br.com.texsistemas.transita.presentation.ui.theme.Typography


@Composable
fun PontoOnibusHorarioFilterChip(
    modifier: Modifier = Modifier,
    horario: Horario,
    isSelected: Boolean,
    horarioStatus: HorarioStatus,
    onClick: (isSelected: Boolean) -> Unit
) {
    val containerColor = when {
        isSelected -> GreenBase
        horarioStatus == HorarioStatus.PASSADO -> LightRed
        else -> Color.White
    }
    val labelColor = if (isSelected) Color.White else Gray400

    FilterChip(
        modifier = modifier
            .padding(2.dp)
            .heightIn(min = 36.dp),
        elevation = FilterChipDefaults.filterChipElevation(
            elevation = 8.dp
        ),
//        leadingIcon = {
//            horario.icon?.let {
//                Icon(
//                    modifier = Modifier.size(16.dp),
//                    painter = painterResource(id = it),
//                    tint = if (isSelected) Color.White else Gray400,
//                    contentDescription = "Ícone de filtro de horário"
//                )
//            }
//        },
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = if (horarioStatus == HorarioStatus.PASSADO && !isSelected) LightRed else Gray300,
            disabledBorderColor = Gray300,
            borderWidth = 1.dp,
            selectedBorderWidth = 0.dp,
            selectedBorderColor = Color.Transparent
        ),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = containerColor,
            selectedContainerColor = GreenBase
        ),
        selected = isSelected,
        onClick = {
            if (horarioStatus != HorarioStatus.PASSADO) {
                onClick(!isSelected)
            }
        },
        label = {
            Text(
                text = horario.horarioFormatado,
                style = Typography.bodyMedium,
                color = labelColor
            )
        }
    )
}

@Preview
@Composable
private fun PontoOnibusHorarioFilterChipPreview() {
    PontoOnibusHorarioFilterChip(
        horario = pontoOnibusMock.horarios[0],
        isSelected = true,
        horarioStatus = HorarioStatus.ATUAL_OU_FUTURO,
        onClick = {}
    )
}

@Preview
@Composable
private fun PontoOnibusHorarioFilterChipNotSelectedPreview() {
    PontoOnibusHorarioFilterChip(
        horario = pontoOnibusMock.horarios[1],
        isSelected = false,
        horarioStatus = HorarioStatus.ATUAL_OU_FUTURO,
        onClick = {}
    )
}

@Preview
@Composable
private fun PontoOnibusHorarioFilterChipNotSelectedPastPreview() {
    val horarioPassado = Horario(id = "3", horario = "08:00", tipoPonto = "EMBARQUE")
    PontoOnibusHorarioFilterChip(
        horario = horarioPassado,
        isSelected = false,
        horarioStatus = HorarioStatus.PASSADO,
        onClick = {}
    )
}