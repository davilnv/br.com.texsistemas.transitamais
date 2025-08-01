package br.com.texsistemas.transita.data.web

import br.com.texsistemas.transita.data.web.dto.LoginRequestDTO
import br.com.texsistemas.transita.data.web.dto.MensagemDTO
import br.com.texsistemas.transita.data.web.dto.PontoOnibusDTO

interface TransitaRemoteDataSource {
    suspend fun postLogin(loginRequest: LoginRequestDTO): Result<MensagemDTO>

    suspend fun getAllPontosOnibusProximos(
        latitudeParameter: Double,
        longitudeParameter: Double,
        raio: Double
    ): Result<List<PontoOnibusDTO>>
}