package br.com.texsistemas.transita.presentation.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.presentation.ui.theme.GreenLight
import br.com.texsistemas.transita.presentation.ui.theme.RedBase
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeUiState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onNavigateToBemVindo: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onEvent(HomeUiEvent.OnFetchDadosMapa)
    }

    LaunchedEffect(key1 = uiState.isDadosMapaCarregado, key2 = uiState.dadosMapaErro) {
        if (uiState.isDadosMapaCarregado) {
            delay(3_000)
            onNavigateToBemVindo()
        } else if (uiState.dadosMapaErro != null) {
            delay(2000)
            onNavigateToBemVindo()
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val scale = infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )

    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )

    val alpha = infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )

    Box(
        modifier = modifier
            .background(GreenLight)
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(214.dp),
                painter = painterResource(R.drawable.logo_text_white),
                contentDescription = "Imagem de logo"
            )
            if (uiState.isCarregandoMapa) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .scale(scale.value)
                        .graphicsLayer(rotationZ = rotation.value)
                        .alpha(alpha.value),
                    painter = painterResource(R.drawable.loader),
                    contentDescription = "Imagem Animada do loader"
                )
            } else if (uiState.dadosMapaErro != null) {
                Text(
                    text = uiState.dadosMapaErro,
                    color = RedBase,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(onNavigateToBemVindo = {}, uiState = HomeUiState(), onEvent = {})
}