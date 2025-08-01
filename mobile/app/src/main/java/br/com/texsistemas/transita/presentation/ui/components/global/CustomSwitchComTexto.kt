package br.com.texsistemas.transita.presentation.ui.components.global

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.GreenBase
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.ui.theme.White

@Composable
fun CustomSwitchComTexto(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textoLigado: String,
    textoDesligado: String,
    largura: Dp = 110.dp,
    altura: Dp = 20.dp
) {
    // Animação da cor de fundo (trilho)
    val corFundo by animateColorAsState(
        targetValue = if (checked) GreenBase else Gray500,
        animationSpec = tween(500)
    )

    // Tamanho da "bolinha" (polegar)
    val tamanhoBolinha = altura - 8.dp

    // Animação do deslocamento da "bolinha"
    val offsetBolinha by animateDpAsState(
        targetValue = if (checked) largura - tamanhoBolinha - 4.dp else 4.dp,
        animationSpec = tween(500)
    )

    // Box principal que funciona como o trilho
    Box(
        modifier = modifier
            .width(largura)
            .height(altura)
            .clip(CircleShape)
            .background(corFundo)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        // Box que representa a "bolinha" (polegar)
        Box(
            modifier = Modifier
                .offset(x = offsetBolinha)
                .size(tamanhoBolinha)
                .clip(CircleShape)
                .background(White)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (checked) Arrangement.Start else Arrangement.End
        ) {
            Text(
                // Mostra o texto correspondente ao estado
                text = if (checked) textoLigado else textoDesligado,
                color = White,
                style = Typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun CustomSwitchComTextoEmbarquePreview() {
    CustomSwitchComTexto(
        modifier = Modifier,
        checked = false,
        onCheckedChange = {},
        textoLigado = "Embarque",
        textoDesligado = "Desembarque"
    )
}

@Preview
@Composable
private fun CustomSwitchComTextoDesembarquePreview() {
    CustomSwitchComTexto(
        modifier = Modifier,
        checked = true,
        onCheckedChange = {},
        textoLigado = "EMBARQUE",
        textoDesligado = "DESEMBARQUE"
    )
}