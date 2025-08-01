package br.com.texsistemas.transita.presentation.ui.components.bemvindo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.Typography

@Composable
fun BemVindoConteudo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Veja como funciona:",
            style = Typography.bodyLarge,
            color = Gray600
        )

        BemVindoDescricao(
            modifier = Modifier.fillMaxWidth(),
            title = "Encontre pontos de ônibus",
            subtitle = "Veja os pontos de ônibus mais próximos de você",
            iconRes = R.drawable.ic_map_pin
        )
        BemVindoDescricao(
            modifier = Modifier.fillMaxWidth(),
            title = "Tenha informações das rotas",
            subtitle = "Veja as rotas dos ônibus de acordo com o ponto selecionado",
            iconRes = R.drawable.ic_alt_route
        )
        BemVindoDescricao(
            modifier = Modifier.fillMaxWidth(),
            title = "Encontre o melhor horário",
            subtitle = "Verifique os horários dos ônibus referente ao ponto e sua rota",
            iconRes = R.drawable.ic_alarm
        )
        Text(
            text = "Algumas permissões precisam ser aceitas para o início.",
            style = Typography.bodyLarge,
            color = Gray600
        )
    }
}