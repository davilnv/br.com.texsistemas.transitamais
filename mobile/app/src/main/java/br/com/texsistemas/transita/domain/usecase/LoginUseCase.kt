package br.com.texsistemas.transita.domain.usecase

import br.com.texsistemas.transita.data.web.dto.MensagemDTO

interface LoginUseCase {
    suspend fun executarLogin(email: String, senha: String): MensagemDTO
}
