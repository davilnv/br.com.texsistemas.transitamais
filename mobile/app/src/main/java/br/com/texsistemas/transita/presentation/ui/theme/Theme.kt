package br.com.texsistemas.transita.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = GreenBase,
    onPrimary = Color.White,
    secondary = RedBase,
    onSecondary = Color.White,
    background = Gray100,
    onBackground = Gray600,
    surface = Gray100,
    onSurface = Gray600,
    error = RedBase,
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = GreenLight,
    onPrimary = Color.Black,
    secondary = RedLight,
    onSecondary = Color.Black,
    background = Gray600,
    onBackground = Gray100,
    surface = Gray600,
    onSurface = Gray200,
    error = RedBase,
    onError = Color.Black
)

@Composable
fun TransitaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = GreenBase,
        backgroundColor = GreenBase.copy(alpha = 0.4f)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        // Aplica as cores de seleção customizadas para o conteúdo total dentro do TransitaTheme
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            content()
        }
    }
}