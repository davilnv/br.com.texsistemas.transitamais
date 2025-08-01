package br.com.texsistemas.transita.presentation.ui.components.global

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.theme.Gray400
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Typography

/**
 * Um componente auxiliar para exibir uma linha com um ícone e um texto.
 */
@Composable
fun InfoLinhaComIcone(
    @DrawableRes iconRes: Int,
    texto: String,
    descricaoConteudo: String? = null,
    tamanhoIcon: Int = 20,
    corIcon: Color = Gray400,
    fonteStyle: TextStyle = Typography.bodyMedium,
    corFonte: Color = Gray500
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = descricaoConteudo,
            modifier = Modifier.size(tamanhoIcon.dp),
            tint = corIcon
        )
        Text(
            text = texto,
            style = fonteStyle,
            color = corFonte,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}