package br.com.texsistemas.transita.data.repository

import br.com.texsistemas.transita.data.web.TransitaRemoteDataSource
import br.com.texsistemas.transita.data.web.dto.LoginRequestDTO
import br.com.texsistemas.transita.data.web.dto.MensagemDTO

class UsuarioRepositoryImpl(private val remoteDataSource: TransitaRemoteDataSource) :
    UsuarioRepository {
    override suspend fun postLogin(loginRequest: LoginRequestDTO): Result<MensagemDTO> {
        return remoteDataSource.postLogin(loginRequest)
    }
}