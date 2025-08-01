package br.com.texsistemas.transita.presentation.ui.components.bemvindo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.Typography

@Composable
fun BemVindoCabecalho(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.icone_transita_mais),
                contentDescription = "Transita App Logo"
            )
        }

        Text(
            text = "Bem-vindo ao Transita+",
            style = Typography.headlineLarge,
            color = Gray600
        )

        Text(
            text = "Obtenha informações sobre pontos, ônibus, rotas, horários e muito mais.",
            style = Typography.bodyLarge,
            color = Gray500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BemVindoCabecalhoPreview() {
    BemVindoCabecalho()
}