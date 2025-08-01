package br.com.texsistemas.transita.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.R
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.presentation.ui.components.global.map.MapView
import br.com.texsistemas.transita.presentation.ui.components.global.pesquisa.BarraPesquisa
import br.com.texsistemas.transita.presentation.ui.components.ponto.PontoCardPrincipal
import br.com.texsistemas.transita.presentation.ui.components.ponto.PontoCardPrincipalSkeleton
import br.com.texsistemas.transita.presentation.ui.components.ponto.TipoPontoSegmentedButton
import br.com.texsistemas.transita.presentation.ui.theme.Gray100
import br.com.texsistemas.transita.presentation.ui.theme.Gray400
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onNavigateToBusStopDetails: (PontoOnibus) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val pontosFiltrados = remember(uiState.pontosOnibus, uiState.tipoPontoSelecionado) {
        uiState.pontosOnibus?.filter { ponto ->
            when (uiState.tipoPontoSelecionado) {
                "EMBARQUE" -> ponto.tipoPonto.equals(
                    "EMBARQUE",
                    ignoreCase = true
                ) || ponto.tipoPonto.equals("AMBOS", ignoreCase = true)

                "DESEMBARQUE" -> ponto.tipoPonto.equals(
                    "DESEMBARQUE",
                    ignoreCase = true
                ) || ponto.tipoPonto.equals("AMBOS", ignoreCase = true)

                else -> true // "AMBOS" mostra todos
            }
        }
    }

    LaunchedEffect(true) {
        onEvent(HomeUiEvent.OnFetchUsuarioLocalizacao)
    }

    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val sheetPeekHeight = with(density) {
        (windowInfo.containerSize.height / 2).toDp()
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetShadowElevation = 8.dp,
        sheetContainerColor = Gray100,
        sheetDragHandle = { BottomSheetDefaults.DragHandle(color = Gray400) },
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Text(
                text = "Encontre paradas perto de você",
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                color = Gray600
            )

            Spacer(modifier = Modifier.height(8.dp))
            TipoPontoSegmentedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp).
                    height(32.dp),
                selectedType = uiState.tipoPontoSelecionado,
                onTypeSelected = { newType ->
                    onEvent(HomeUiEvent.OnTipoPontoChange(newType))
                }
            )

            // Lista de Pontos
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (pontosFiltrados.isNullOrEmpty()) {
                    items(7) {
                        PontoCardPrincipalSkeleton()
                    }
                } else {
                    items(pontosFiltrados) { ponto ->
                        PontoCardPrincipal(
                            nome = ponto.titulo,
                            descricao = ponto.descricao,
                            rating = ponto.avaliacao,
                            imageRes = R.drawable.img_sem_imagem,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onNavigateToBusStopDetails(ponto)
                                },
                            imagemLocal = ponto.imagemLocal,
                            linkImagem = ponto.linkImagem,
                            pontoId = ponto.id,
                            onImagemLocalAtualizada = { pontoId, imagemLocal ->
                                onEvent(HomeUiEvent.AtualizarImagemLocal(pontoId, imagemLocal))
                            }
                        )
                    }
                }
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = it
                            .calculateBottomPadding()
                            .minus(26.dp)
                    )
            ) {
                if (uiState.isDadosMapaCarregado && uiState.userMapIcon != null && uiState.pinMapIcon != null) {
                    MapView(
                        modifier = Modifier.fillMaxSize(),
                        zoom = uiState.mapZoom,
                        center = uiState.usuarioPosicao,
                        pontosOnibus = pontosFiltrados ?: emptyList(), // Usar pontos filtrados
                        userIcon = uiState.userMapIcon,
                        pinIcon = uiState.pinMapIcon,
                        onZoomChanged = { newZoom ->
                            onEvent(HomeUiEvent.OnZoomChanged(newZoom))
                        }
                    )
                } else if (uiState.dadosMapaErro != null) {
                    Text("Erro ao carregar o mapa: ${uiState.dadosMapaErro}")
                }

                BarraPesquisa(
                    value = "",
                    onValueChange = {},
                    onSearchClicked = {},
                    onMenuClicked = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onNavigateToBusStopDetails = {},
        uiState = HomeUiState(),
        onEvent = {}
    )
}