package br.com.texsistemas.transita.presentation.ui.components.ponto

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.theme.Gray200
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.ui.theme.White
import androidx.compose.foundation.shape.RoundedCornerShape
import br.com.texsistemas.transita.presentation.ui.theme.GreenLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPontoSegmentedButton(
    modifier: Modifier = Modifier,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    val types = listOf("EMBARQUE", "AMBOS", "DESEMBARQUE")

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        types.forEachIndexed { index, label ->
            val shape = when (index) {
                0 -> RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                types.lastIndex -> RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                else -> RoundedCornerShape(0.dp)
            }
            SegmentedButton(
                shape = shape,
                onClick = { onTypeSelected(label) },
                selected = label == selectedType,
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = GreenLight,
                    activeContentColor = White,
                    inactiveContainerColor = White,
                    inactiveContentColor = Gray500
                ),
                border = SegmentedButtonDefaults.borderStroke(
                    color = Gray200,
                    width = 1.dp
                )
            ) {
                Text(text = label, style = Typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
private fun TipoPontoSegmentoButtonPreview() {
    TipoPontoSegmentedButton(
        modifier = Modifier.height(48.dp),
        selectedType = "AMBOS",
        onTypeSelected = {}
    )
}