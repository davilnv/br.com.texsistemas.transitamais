package br.com.texsistemas.transita

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.texsistemas.transita.presentation.ui.common.hasLocationPermission
import br.com.texsistemas.transita.presentation.ui.route.UIRoutes
import br.com.texsistemas.transita.presentation.ui.screens.BemVindoScreen
import br.com.texsistemas.transita.presentation.ui.screens.DetalhesPontoOnibusScreen
import br.com.texsistemas.transita.presentation.ui.screens.HomeScreen
import br.com.texsistemas.transita.presentation.ui.screens.LocalizacaoPontoScreen
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeViewModel
import br.com.texsistemas.transita.presentation.ui.screens.LoginScreen
import br.com.texsistemas.transita.presentation.ui.screens.SplashScreen
import br.com.texsistemas.transita.presentation.ui.screens.VeiculoScreen
import br.com.texsistemas.transita.presentation.ui.theme.TransitaTheme
import br.com.texsistemas.transita.presentation.viewmodel.login.LoginViewModel
import br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus.PontoOnibusViewModel
import br.com.texsistemas.transita.presentation.viewmodel.veiculo.VeiculoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
) {
    TransitaTheme {
        val context = LocalContext.current

        val navController = rememberNavController()

        val loginViewModel = koinViewModel<LoginViewModel>()
        val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()

        val homeViewModel = koinViewModel<HomeViewModel>()
        val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

        val pontoOnibusViewModel = koinViewModel<PontoOnibusViewModel>()
        val pontoOnibusUiState by pontoOnibusViewModel.uiState.collectAsStateWithLifecycle()

        val veiculoViewModel = koinViewModel<VeiculoViewModel>()
        val veiculoUiState by veiculoViewModel.uiState.collectAsStateWithLifecycle()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) { innerPadding ->
            // Use the innerPadding to apply padding to your content
            NavHost(
                navController = navController,
                startDestination = UIRoutes.SPLASH
            ) {
                composable(route = UIRoutes.SPLASH) {
                    SplashScreen(
                        onNavigateToBemVindo = {
                            if (hasLocationPermission(context)) {
                                navController.navigate(UIRoutes.HOME) {
                                    popUpTo(UIRoutes.SPLASH) { inclusive = true }
                                }
                            } else {
                                navController.navigate(UIRoutes.BEM_VINDO) {
                                    popUpTo(UIRoutes.SPLASH) { inclusive = true }
                                }
                            }
                        },
                        uiState = homeUiState,
                        onEvent = homeViewModel::onEvent
                    )
                }

                composable(route = UIRoutes.BEM_VINDO) {
                    BemVindoScreen(
                        onNavigateToHome = {
                            navController.navigate(UIRoutes.HOME)
                        },
                        uiState = homeUiState,
                        onEvent = homeViewModel::onEvent,
                    )
                }

                composable(route = UIRoutes.LOGIN) {
                    LoginScreen(
                        onNavigateToHome = {
                            navController.navigate(UIRoutes.HOME)
                        },
                        uiState = loginUiState,
                        onEvent = loginViewModel::onEvent
                    )
                }

                composable(route = UIRoutes.HOME) {
                    HomeScreen(
                        onNavigateToBusStopDetails = { selectedPontoOnibus ->
                            navController.navigate("${UIRoutes.PONTO_ONIBUS_DETALHE}/${selectedPontoOnibus.id}")
                        },
                        uiState = homeUiState,
                        onEvent = homeViewModel::onEvent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                composable("${UIRoutes.PONTO_ONIBUS_DETALHE}/{pontoId}") { backStackEntry ->
                    val pontoId = backStackEntry.arguments?.getString("pontoId")
                    val pontoOnibus = pontoOnibusUiState.pontoOnibus

                    // Se o pontoOnibus do estado não for o mesmo do id, atualiza
                    if (pontoId != null && (pontoOnibus == null || pontoOnibus.id != pontoId)) {
                        homeViewModel.getPontoOnibusById(pontoId)?.let { ponto ->
                            pontoOnibusViewModel.setPontoOnibusSelecionado(ponto)
                        }
                    }

                    if (pontoOnibus != null && pontoOnibus.id == pontoId) {
                        DetalhesPontoOnibusScreen(
                            pontoOnibus = pontoOnibus,
                            uiState = pontoOnibusUiState,
                            onEvent = pontoOnibusViewModel::onEvent,
                            onNavigateBack = { navController.popBackStack() },
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToLocalizacao = { selectedPontoOnibus, horarioSelecionado ->
                                navController.navigate("${UIRoutes.LOCALIZACAO_PONTO_ONIBUS}/${selectedPontoOnibus.id}?horario=${horarioSelecionado ?: ""}")
                            },
                            onNavigateToVeiculo = { veiculoId ->
                                navController.navigate("${UIRoutes.VEICULO_ROTAS}/${veiculoId}")
                            }
                        )
                    }
                }

                composable("${UIRoutes.LOCALIZACAO_PONTO_ONIBUS}/{pontoId}?horario={horario}") { backStackEntry ->
                    val pontoId = backStackEntry.arguments?.getString("pontoId")
                    val pontoOnibus = pontoId?.let { homeViewModel.getPontoOnibusById(it) }
                    val proximoHorario = backStackEntry.arguments?.getString("horario") ?: "--:--"

                    if (pontoOnibus != null) {
                        LocalizacaoPontoScreen(
                            modifier = Modifier.padding(innerPadding),
                            pontoOnibus = pontoOnibus,
                            proximoHorario = proximoHorario,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }

                composable("${UIRoutes.VEICULO_ROTAS}/{veiculoId}") { backStackEntry ->
                    val veiculoId = backStackEntry.arguments?.getString("veiculoId")
                    if (veiculoId != null) {
                        VeiculoScreen(
                            modifier = Modifier.padding(innerPadding),
                            veiculoId = veiculoId,
                            uiState = veiculoUiState,
                            onEvent = veiculoViewModel::onEvent,
                            onNavigateBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}