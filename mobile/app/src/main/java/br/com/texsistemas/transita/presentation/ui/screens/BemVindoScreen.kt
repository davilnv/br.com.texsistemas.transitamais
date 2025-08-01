package br.com.texsistemas.transita.presentation.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.components.global.TransitaButton
import br.com.texsistemas.transita.presentation.ui.components.bemvindo.BemVindoCabecalho
import br.com.texsistemas.transita.presentation.ui.components.bemvindo.BemVindoConteudo
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeUiState

@Composable
fun BemVindoScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {
    var shouldRequestPermission by remember { mutableStateOf(false) }
    var showPermissionDeniedDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onEvent(HomeUiEvent.OnFetchUsuarioLocalizacao)
            onNavigateToHome()
        } else {
            showPermissionDeniedDialog = true
        }
    }

    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 48.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BemVindoCabecalho()
        BemVindoConteudo()
        TransitaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Começar",
            onClick = { shouldRequestPermission = true }
        )
    }

    if (shouldRequestPermission) {
        LaunchedEffect(Unit) {
            launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            shouldRequestPermission = false
        }
    }

    if (showPermissionDeniedDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDeniedDialog = false },
            title = { Text("Permissão necessária") },
            text = { Text("Para continuar, é necessário permitir o acesso à localização.") },
            confirmButton = {
                Button(onClick = { showPermissionDeniedDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview
@Composable
private fun BemVindoScreenPreview() {
    BemVindoScreen(onNavigateToHome = {}, uiState = HomeUiState(), onEvent = {})
}