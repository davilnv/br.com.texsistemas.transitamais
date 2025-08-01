package br.com.texsistemas.transita.domain.service

import br.com.texsistemas.transita.data.repository.PontoOnibusRepository
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.usecase.GetPontoOnibusUseCase

class PontoOnibusService(
    private val repository: PontoOnibusRepository
) :
    GetPontoOnibusUseCase {
    override suspend fun buscarPontosOnibusProximos(
        latitude: Double,
        longitude: Double,
        raio: Double
    ): List<PontoOnibus> {
        return repository.getAllPontosOnibusProximos(latitude, longitude, raio)
    }
}