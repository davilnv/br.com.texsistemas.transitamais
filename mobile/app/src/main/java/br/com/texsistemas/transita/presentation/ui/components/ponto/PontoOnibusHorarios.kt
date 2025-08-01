package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.presentation.ui.common.mock.pontoOnibusMock
import br.com.texsistemas.transita.presentation.ui.theme.Gray400
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Typography

@Composable
fun PontoOnibusDetalhesInfo(
    modifier: Modifier = Modifier,
    ponto: PontoOnibus
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Informações", style = Typography.headlineSmall, color = Gray500)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = ponto.informacao,
                style = Typography.bodyMedium,
                color = Gray500
            )
        }

        Text(text = "Localização", style = Typography.headlineSmall, color = Gray500)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(

                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_map_pin),
                    tint = Gray400,
                    contentDescription = "Icone Endereço"
                )
                Text(
                    text = ponto.endereco.toString(),
                    style = Typography.labelMedium,
                    color = Gray500
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoOnibusDetalhesInfoPreview() {
    PontoOnibusDetalhesInfo(
        modifier = Modifier.fillMaxWidth(),
        ponto = pontoOnibusMock
    )
}