package br.com.texsistemas.transita.domain.usecase

import br.com.texsistemas.transita.domain.model.Veiculo

interface GetVeiculoUseCase {
    suspend fun buscarVeiculoPorId(id: String): Veiculo
}