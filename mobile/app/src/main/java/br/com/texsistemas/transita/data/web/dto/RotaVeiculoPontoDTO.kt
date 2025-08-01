package br.com.texsistemas.transita.data.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class RotaVeiculoPontoDTO(
    val veiculoId: String,
    val horarioId: String,
    val pontoId: String,
    val ordem: Int
)
