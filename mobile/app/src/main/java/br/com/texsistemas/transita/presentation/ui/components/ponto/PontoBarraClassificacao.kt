package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.GreenLight

@Composable
fun PontoBarraClassificacao(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Row(modifier = modifier) {
        repeat(maxRating) { index ->
            val icon = if (index < rating.toInt()) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.StarBorder
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (rating <= 0) Gray500 else GreenLight
            )
        }
    }
}

@Preview
@Composable
private fun PontoBarraClassificacaoPreview() {
    PontoBarraClassificacao(3f)
}


@Preview
@Composable
private fun PontoBarraClassificacaoZeroRatingPreview() {
    PontoBarraClassificacao(0f)
}