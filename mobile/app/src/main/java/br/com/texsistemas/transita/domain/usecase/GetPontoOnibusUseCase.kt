package br.com.texsistemas.transita.domain.usecase

import br.com.texsistemas.transita.domain.model.PontoOnibus

interface GetPontoOnibusUseCase {
    suspend fun buscarPontosOnibusProximos(
        latitude: Double,
        longitude: Double,
        raio: Double
    ): List<PontoOnibus>
}