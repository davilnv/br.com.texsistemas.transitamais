package br.com.texsistemas.transita.data.repository

import br.com.texsistemas.transita.data.web.dto.LoginRequestDTO
import br.com.texsistemas.transita.data.web.dto.MensagemDTO

interface UsuarioRepository {
    suspend fun postLogin(loginRequest: LoginRequestDTO): Result<MensagemDTO>
}