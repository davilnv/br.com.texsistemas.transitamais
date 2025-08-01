package br.com.texsistemas.transita.presentation.ui.components.global.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import br.com.texsistemas.transita.presentation.ui.theme.Gray100
import br.com.texsistemas.transita.presentation.ui.theme.Gray400

/**
 * Um layout de tela reutilizável que exibe um mapa no fundo,
 * um painel de informações (bottom sheet) e conteúdo flutuante.
 *
 * @param modifier O modificador a ser aplicado ao layout.
 * @param mapCenter As coordenadas (latitude, longitude) da localização do usuário para centralizar o mapa.
 * @param pontosOnibus A lista dos pontos de ônibus a serem exibidos no mapa.
 * @param mapZoom O nível de zoom inicial do mapa.
 * @param sheetPeekHeight A altura que o painel inferior deve ter quando minimizado. Por padrão, ocupa 50% da tela.
 * @param sheetShape O formato das bordas do painel inferior.
 * @param sheetContainerColor A cor de fundo do painel inferior.
 * @param overlayContent O conteúdo Composable que será exibido sobre o mapa (ex: Barra de Pesquisa, Botão de Voltar).
 * @param useSheet Se true, usa o BottomSheetScaffold que permite arrastar o conteúdo.
 *                 Se false, exibe o 'sheetContent' como um card fixo na parte inferior.
 * @param sheetContent O conteúdo Composable que será exibido no painel inferior ou como componente sobreposto do mapa.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaLayoutScreen(
    modifier: Modifier = Modifier,
    mapCenter: UsuarioPosicao? = null,
    useUserIcon: Boolean = true,
    pontosOnibus: List<PontoOnibus> = emptyList(),
    mapZoom: Float = 15.0f,
    sheetPeekHeight: Dp = LocalConfiguration.current.screenHeightDp.dp * 0.5f,
    sheetShape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetContainerColor: Color = Gray100,
    overlayContent: @Composable (padding: PaddingValues) -> Unit,
    useSheet: Boolean = true,
    sheetContent: @Composable (() -> Unit)? = null
) {
    // Mapa utilizado nos dois modos (com ou sem Bottom Sheet)
    val mapa = @Composable {
        MapView(
            modifier = Modifier.fillMaxSize(),
            zoom = mapZoom,
            center = mapCenter,
            pontosOnibus = pontosOnibus,
            context = LocalContext.current,
            hasUserIcon = useUserIcon
        )
    }

    // Conteúdo do Bottom Sheet ou Card flutuante
    if (useSheet) {
        // Com Bottom Sheet
        val bottomSheetState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
            modifier = modifier,
            scaffoldState = bottomSheetState,
            sheetContainerColor = sheetContainerColor,
            sheetPeekHeight = if (sheetContent != null) sheetPeekHeight else 0.dp,
            sheetShape = sheetShape,
            sheetDragHandle = {
                if (sheetContent != null) BottomSheetDefaults.DragHandle(color = Gray400)
            },
            sheetContent = { sheetContent?.invoke() },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding().minus(26.dp))
            ) {
                mapa()
                overlayContent(paddingValues)
            }
        }
    } else {
        // Componente flutuante sem Bottom Sheet
        Box(modifier = modifier.fillMaxSize()) {
            //O Mapa como camada de fundo
            mapa()

            //Conteúdo flutuante sobre o mapa (ex: botão de voltar)
            overlayContent(PaddingValues(0.dp))

            //Componente flutuante na parte inferior
            if (sheetContent != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 32.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    sheetContent()
                }
            }
        }
    }
}