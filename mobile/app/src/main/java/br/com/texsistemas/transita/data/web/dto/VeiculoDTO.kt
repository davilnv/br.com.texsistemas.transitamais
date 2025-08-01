package br.com.texsistemas.transita.data.web.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class VeiculoDTO(
    val id: String,
    val placa: String,
    val tipoTransporte: String,
    val capacidade: Int,
    val ativo: Boolean,
    @Contextual val dataCriacao: LocalDateTime,
    val informacoesVeiculos: InformacoesVeiculosDTO,
    val horarios: MutableList<HorarioVeiculoDTO>,
    val rotaVeiculoPonto: MutableList<RotaVeiculoPontoDTO>
)

@Serializable
data class InformacoesVeiculosDTO(
    val latitude: Double,
    val longitude: Double,
    @Contextual val dataRegistro: LocalDateTime,
    @Contextual val dataRegistroProxPonto: LocalDateTime,
    val quantidadePassageiros: Int,
    val horarioAtualId: String
)

@Serializable
data class HorarioVeiculoDTO(
    val id: String,
    val horarioId: String
)