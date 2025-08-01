package br.com.texsistemas.transita.domain.service

import br.com.texsistemas.transita.data.repository.VeiculoRepository
import br.com.texsistemas.transita.domain.model.Veiculo
import br.com.texsistemas.transita.domain.usecase.GetVeiculoUseCase

class VeiculoService(
    private val repository: VeiculoRepository
) :
    GetVeiculoUseCase {
    override suspend fun buscarVeiculoPorId(id: String): Veiculo {
        return repository.getVeiculoById(id)
    }
}