package br.com.texsistemas.transita.presentation.ui.components.global

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.presentation.ui.theme.GreenLight
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.ui.theme.White

@Composable
fun TransitaButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.heightIn(min = 56.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = if (text == null && iconRes != null) PaddingValues(0.dp) else ButtonDefaults.ContentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenLight
        ),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            iconRes?.let {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Ícone do botão",
                    tint = White
                )
            }
            text?.let {
                Text(
                    text = text.uppercase(),
                    style = Typography.labelLarge,
                    color = White
                )
            }
        }
    }
}

@Preview
@Composable
private fun TransitaButtonPreview() {
    TransitaButton(
        modifier = Modifier.fillMaxWidth(), text = "Confirmar", iconRes = R.drawable.ic_scan
    ) {}
}

@Preview
@Composable
private fun TransitaButtonNoIconPreview() {
    TransitaButton(
        modifier = Modifier.fillMaxWidth(), text = "Confirmar"
    ) {}
}

@Preview
@Composable
private fun TransitaButtonNoTextPreview() {
    TransitaButton(
        modifier = Modifier, iconRes = R.drawable.ic_arrow_left
    ) {}
}