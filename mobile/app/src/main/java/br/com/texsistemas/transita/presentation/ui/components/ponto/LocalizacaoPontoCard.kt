package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.presentation.ui.components.global.InfoLinhaComIcone
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.GreenBase
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.ui.theme.White

@Composable
fun LocalizacaoPontoCard(
    modifier: Modifier = Modifier,
    nome: String,
    endereco: String,
    proximoHorario: String,
    classificacao: Float
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Linha com Nome do Ponto e Classificação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nome,
                    style = Typography.headlineMedium,
                    color = Gray600
                )
                PontoBarraClassificacao(rating = classificacao)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Linha de Informação: Endereço
            InfoLinhaComIcone(
                iconRes = R.drawable.ic_map_pin,
                texto = endereco
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Linha de Informação: Próximo Horário
            InfoLinhaComIcone(
                iconRes = R.drawable.ic_bus,
                texto = "Próximo: $proximoHorario",
                corIcon = GreenBase
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocalizacaoPontoCardPreview() {
    LocalizacaoPontoCard(
        nome = "Santa",
        endereco = "R. Severino Pereira Lins, 1156 - Alto da Conceicao, Serra Talhada - PE, 56900-000",
        proximoHorario = "10:30",
        classificacao = 3.5f
    )
}