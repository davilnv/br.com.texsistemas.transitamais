package br.com.texsistemas.transita.domain.usecase

import br.com.texsistemas.transita.domain.model.UsuarioPosicao

interface GetUsuarioLocalizacaoUseCase {
    suspend fun solicitarLocalizacao(): UsuarioPosicao?
}