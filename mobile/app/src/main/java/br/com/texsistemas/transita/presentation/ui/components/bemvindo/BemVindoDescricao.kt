package br.com.texsistemas.transita.presentation.ui.components.bemvindo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.RedBase
import br.com.texsistemas.transita.presentation.ui.theme.Typography

@Composable
fun BemVindoDescricao(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    @DrawableRes iconRes: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(iconRes),
            tint = RedBase,
            contentDescription = "Ícone descrição",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = Typography.headlineSmall, color = Gray600)
            Text(text = subtitle, color = Gray500, style = Typography.bodyLarge)
        }
    }
}