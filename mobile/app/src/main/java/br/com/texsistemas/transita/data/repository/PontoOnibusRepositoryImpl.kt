package br.com.texsistemas.transita.data.repository

import android.util.Log
import br.com.texsistemas.transita.data.local.dao.PontoOnibusDao
import br.com.texsistemas.transita.data.web.TransitaRemoteDataSource
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.toDomain

class PontoOnibusRepositoryImpl(
    private val remoteDataSource: TransitaRemoteDataSource,
    private val localDataSource: PontoOnibusDao
) :
    PontoOnibusRepository {
    override suspend fun getAllPontosOnibusProximos(
        latitude: Double,
        longitude: Double,
        raio: Double // Valor padrão de 1 km
    ): List<PontoOnibus> {
        try {
            // Tenta buscar dados da rede primeiro
            val remoteResponse =
                remoteDataSource.getAllPontosOnibusProximos(latitude, longitude, raio)

            if (remoteResponse.isSuccess) {
                val pontosRemotosDTO = remoteResponse.getOrThrow()
                // Busca os pontos locais existentes para preservar imagemLocal
                val pontosLocais = localDataSource.getAllPontosOnibus().associateBy { it.id }
                val domainPontos = pontosRemotosDTO.map { dto ->
                    val local = pontosLocais[dto.id]
                    dto.toDomain().copy(imagemLocal = local?.imagemLocal)
                }

                // Salva os dados na base de dados local para cache
                localDataSource.insertPontosOnibus(domainPontos)
                Log.d(
                    "PontoOnibusRepositoryImpl",
                    "Dados de pontos de ônibus obtidos da rede e salvos localmente."
                )
                return domainPontos
            } else {
                // Se a busca remota falhar (ex: sem internet), tenta buscar dados localmente
                val localPontos = localDataSource.getAllPontosOnibus()
                if (localPontos.isNotEmpty()) {
                    Log.d(
                        "PontoOnibusRepositoryImpl",
                        "Rede falhou, dados de pontos de ônibus encontrados localmente."
                    )
                    return localPontos
                } else {
                    // Se não houver dados locais e a rede falhou, lança a exceção original da rede
                    val errorMessage =
                        remoteResponse.exceptionOrNull()?.message ?: "Erro desconheido na rede."
                    Log.e(
                        "PontoOnibusRepositoryImpl",
                        "Rede e cache local falharam. Erro remoto: $errorMessage"
                    )
                    throw Exception("Não foi possível carregar os pontos de ônibus, mesmo offline. Detalhes: $errorMessage")
                }
            }
        } catch (e: Exception) {
            // Captura qualquer outra exceção (ex: conversão, banco de dados) ou da rede se não for Result.failure
            Log.e(
                "PontoOnibusRepositoryImpl",
                "Exceção inesperada no repositório, tentando cache local. Erro: ${e.message}",
                e
            )
            val localPontos = localDataSource.getAllPontosOnibus()
            if (localPontos.isNotEmpty()) {
                Log.d(
                    "PontoOnibusRepositoryImpl",
                    "Erro, mas dados de pontos de ônibus obtidos do cache local como fallback final."
                )
                return localPontos
            } else {
                Log.e(
                    "PontoOnibusRepositoryImpl",
                    "Erro e cache local vazio. Não há dados para exibir. Erro: ${e.message}"
                )
                throw e
            }
        }
    }

    override suspend fun getPontosOnibusByIds(ids: List<String>): List<PontoOnibus> {
        return localDataSource.getPontosOnibusByIds(ids)
    }
}