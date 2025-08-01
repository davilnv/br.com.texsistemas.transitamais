package br.com.texsistemas.transita.data.repository

import br.com.texsistemas.transita.data.local.dao.VeiculoDao
import br.com.texsistemas.transita.data.web.TransitaRemoteDataSource
import br.com.texsistemas.transita.domain.model.Veiculo
import br.com.texsistemas.transita.presentation.ui.common.mock.veiculoMock

class VeiculoRepositoryImpl(
    private val remoteDataSource: TransitaRemoteDataSource,
    private val localDataSource: VeiculoDao
) : VeiculoRepository {
    override suspend fun getVeiculoById(id: String): Veiculo {
        // todo: Implementar a lógica para buscar da API e/ou cache local. Por agora, podemos usar um mock:
        return veiculoMock
    }
}