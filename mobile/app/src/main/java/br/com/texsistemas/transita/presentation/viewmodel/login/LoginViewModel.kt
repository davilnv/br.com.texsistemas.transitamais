package br.com.texsistemas.transita.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.texsistemas.transita.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val loginUseCase: LoginUseCase by inject()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnLoginClicked -> executarLogin(
                email = event.email,
                senha = event.senha
            )

            is LoginUiEvent.OnEmailChanged -> atualizarEmail(event.email)
            is LoginUiEvent.OnSenhaChanged -> atualizarSenha(event.senha)
        }
    }

    private fun executarLogin(email: String, senha: String) {
        viewModelScope.launch {
            val result = loginUseCase.executarLogin(email, senha)
            _uiState.update { currentUiState ->
                if (result.sucesso) {
                    currentUiState.copy(
                        isCarregando = true,
                        mensagemErro = null
                    )
                } else {
                    currentUiState.copy(mensagemErro = result.mensagem)
                }
            }
        }
    }

    private fun atualizarEmail(email: String) {
        _uiState.update { currentUiState ->
            currentUiState.copy(email = email)
        }
    }

    private fun atualizarSenha(senha: String) {
        _uiState.update { currentUiState ->
            currentUiState.copy(senha = senha)
        }
    }

}