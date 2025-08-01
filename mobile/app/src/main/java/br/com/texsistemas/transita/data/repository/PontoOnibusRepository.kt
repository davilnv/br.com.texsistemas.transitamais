package br.com.texsistemas.transita.data.repository

import br.com.texsistemas.transita.domain.model.PontoOnibus

interface PontoOnibusRepository {
    suspend fun getAllPontosOnibusProximos(
        latitude: Double,
        longitude: Double,
        raio: Double = 1.0 // Valor padrão de 1 km
    ): List<PontoOnibus>

    suspend fun getPontosOnibusByIds(ids: List<String>): List<PontoOnibus>
}