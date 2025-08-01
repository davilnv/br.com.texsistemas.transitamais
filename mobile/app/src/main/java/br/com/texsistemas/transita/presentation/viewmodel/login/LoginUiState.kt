package br.com.texsistemas.transita.presentation.viewmodel.login

data class LoginUiState(
    val isCarregando: Boolean = false,
    val email: String = "",
    val senha: String = "",
    val mensagemErro: String? = null
)