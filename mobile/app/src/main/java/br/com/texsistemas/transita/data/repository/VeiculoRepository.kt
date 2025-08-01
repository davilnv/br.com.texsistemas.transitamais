package br.com.texsistemas.transita.data.repository

import br.com.texsistemas.transita.domain.model.Veiculo

interface VeiculoRepository {
    suspend fun getVeiculoById(id: String): Veiculo
}