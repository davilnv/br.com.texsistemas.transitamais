package br.com.texsistemas.transita.presentation.ui.components.global.pesquisa

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.GreenBase
import br.com.texsistemas.transita.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraPesquisa(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "Busque um transporte",
    onValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit = {},
    onMenuClicked: () -> Unit = {},
    trailingContent: @Composable (() -> Unit)? = null
) {
    var isExpanded by remember { mutableStateOf(false) }

    DockedSearchBar(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = SearchBarDefaults.dockedShape),
        colors = SearchBarDefaults.colors(
            containerColor = White,
            dividerColor = Gray500,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                focusedTextColor = Gray500,
                unfocusedTextColor = Gray500,
                cursorColor = GreenBase,
                disabledPlaceholderColor = Gray500,
                focusedLeadingIconColor = Gray500, // Adicionado
                unfocusedLeadingIconColor = Gray500, // Adicionado
                focusedTrailingIconColor = Gray500, // Adicionado
                unfocusedTrailingIconColor = Gray500 // Adicionado
            )
        ),
        inputField = {
            SearchBarDefaults.InputField(
                query = value,
                onQueryChange = onValueChange,
                onSearch = {
//                    onSearchClicked(it)
                    isExpanded = false
                },
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                placeholder = { Text(placeholder, color = Gray500) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.clickable { onMenuClicked() },
                        tint = Gray500
                    )
                },
                trailingIcon = {
                    trailingContent ?: Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Gray500
                    )
                }
            )
        },
        content = {
            // Aqui você coloca sugestões ou resultados se quiser
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BarraPesquisaPreview() {
    BarraPesquisa(
        onValueChange = {},
        value = "Pesquisar"
    )
}