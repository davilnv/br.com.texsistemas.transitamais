package br.com.texsistemas.transita.domain.service

import br.com.texsistemas.transita.data.repository.UsuarioLocalizacaoRepository
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import br.com.texsistemas.transita.domain.usecase.GetUsuarioLocalizacaoUseCase

class UsuarioLocalizacaoService(private val repository: UsuarioLocalizacaoRepository) :
    GetUsuarioLocalizacaoUseCase {

    override suspend fun solicitarLocalizacao(): UsuarioPosicao? {
        return repository.getLocalizacaoAtual()
    }

}