package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.core.common.baseIp
import br.com.texsistemas.transita.core.util.ImagemUtil
import br.com.texsistemas.transita.presentation.ui.components.global.shimmerEffect
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.ui.theme.White
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun PontoCardPrincipal(
    modifier: Modifier = Modifier,
    nome: String,
    descricao: String,
    rating: Float,
    imageRes: Int,
    imagemLocal: String? = null,
    linkImagem: String? = null,
    pontoId: String? = null,
    onImagemLocalAtualizada: ((String, String) -> Unit)? = null
) {
    var localImagePath by remember { mutableStateOf(imagemLocal) }
    val coroutineScope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    // Se não tem imagem local mas tem link, tenta baixar e salvar usando ImagemUtil.baixarEArmazenarImagem
    if (imagemLocal == null) {
        LaunchedEffect(linkImagem) {
            if (localImagePath == null && !linkImagem.isNullOrBlank() && pontoId != null && onImagemLocalAtualizada != null) {
                coroutineScope.launch {
                    try {
                        val nomeArquivo = "ponto_${nome.hashCode()}.png"
                        val caminho = ImagemUtil.baixarEArmazenarImagem(
                            context,
                            baseIp + linkImagem,
                            nomeArquivo
                        )
                        if (!caminho.isNullOrBlank()) {
                            localImagePath = caminho
                            onImagemLocalAtualizada.invoke(pontoId, caminho)
                        }
                    } catch (_: Exception) {
                    }
                }
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White // Fundo branco
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            if (localImagePath != null) {
                AsyncImage(
                    model = File(localImagePath!!),
                    contentDescription = stringResource(R.string.content_description_capa_ponto_onibus),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    placeholder = painterResource(R.drawable.img_sem_imagem),
                    error = painterResource(R.drawable.img_sem_imagem)
                )
            } else {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = stringResource(R.string.content_description_capa_ponto_onibus),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = nome,
                    style = Typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Gray600
                )

                Text(
                    text = descricao,
                    style = Typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray500
                )

                Spacer(modifier = Modifier.height(4.dp))

                PontoBarraClassificacao(
                    rating = rating
                )
            }
        }
    }
}

@Composable
fun PontoCardPrincipalSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Esqueleto para a Imagem
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                // Esqueleto para o Texto do Nome
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Esqueleto para a Descrição (simulando 2 linhas)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )


                Spacer(modifier = Modifier.height(8.dp))

                // Esqueleto para a Barra de Classificação
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

// Preview para ver como ficou!
@Preview(showBackground = true)
@Composable
private fun PontoCardPrincipalSkeletonPreview() {
    PontoCardPrincipalSkeleton()
}

@Preview
@Composable
private fun PontoCardPrincipalPreview() {
    PontoCardPrincipal(
        nome = "Santa",
        descricao = "Local de fácil acesso para pegar o ônibus nos bairros próximos.",
        rating = 4f,
        imageRes = R.drawable.img_santa
    )

}
