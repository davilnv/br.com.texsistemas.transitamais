package br.com.texsistemas.transita.data.repository

import br.com.texsistemas.transita.domain.model.UsuarioPosicao

interface UsuarioLocalizacaoRepository {
    suspend fun getLocalizacaoAtual(): UsuarioPosicao?
}