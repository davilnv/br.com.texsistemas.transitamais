package br.com.texsistemas.transita.presentation.viewmodel.login

sealed class LoginUiEvent {
    data class OnEmailChanged(val email: String) : LoginUiEvent()
    data class OnSenhaChanged(val senha: String) : LoginUiEvent()
    data class OnLoginClicked(val email: String, val senha: String) : LoginUiEvent()
}