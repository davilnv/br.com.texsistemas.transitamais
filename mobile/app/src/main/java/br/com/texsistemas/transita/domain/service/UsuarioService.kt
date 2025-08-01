package br.com.texsistemas.transita.domain.service

import android.util.Log
import br.com.texsistemas.transita.data.repository.UsuarioRepository
import br.com.texsistemas.transita.data.web.dto.LoginRequestDTO
import br.com.texsistemas.transita.data.web.dto.MensagemDTO
import br.com.texsistemas.transita.domain.usecase.LoginUseCase
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException

class UsuarioService(private val userRepository: UsuarioRepository) : LoginUseCase {
    override suspend fun executarLogin(email: String, senha: String): MensagemDTO {
        return userRepository.postLogin(LoginRequestDTO(email, senha)).fold(
            onSuccess = { response ->
                Log.d("UserService", "response: $response")
                response
            },
            onFailure = { error ->
                Log.d("UserService", "error: $error")
                val errorMessage =
                    (error as? ResponseException)?.response?.body<MensagemDTO>()?.mensagem
                val codigo = (error as? ResponseException)?.response?.status?.value
                MensagemDTO(
                    codigo = codigo,
                    mensagem = errorMessage ?: "Erro ao realizar login",
                    sucesso = false,
                )
            }
        )
    }
}