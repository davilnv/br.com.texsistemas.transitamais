package br.com.texsistemas.transita.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.texsistemas.transita.R

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold)
)

private const val activatePreview = true

// Set of Material typography styles to start with
val Typography = Typography(
    // "Title XI"
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    // "Title Lg"
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    // "Title Md"
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    // "Text Sm"
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    // "Text Md"
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // "Text Sm"
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    // "Text Xs"
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    // "Action"
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    // "Subtitle"
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)