package br.com.texsistemas.transita.presentation.ui.components.global

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Função para criar o Modifier do shimmer
fun Modifier.shimmerEffect(): Modifier = composed {
    // Lista de cores para o gradiente do shimmer
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    // Controlador de animação infinita
    val transition = rememberInfiniteTransition(label = "ShimmerTransition")

    // Animação que move o gradiente
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000), // Duração da animação
        ),
        label = "ShimmerTranslateAnimation"
    )

    // O "pincel" (Brush) que será aplicado como fundo
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation, y = translateAnimation)
    )

    // Retorna o modifier do background com o nosso pincel
    this.background(brush = brush)
}