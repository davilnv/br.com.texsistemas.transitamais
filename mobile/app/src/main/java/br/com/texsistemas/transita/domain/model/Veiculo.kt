package br.com.texsistemas.transita.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.texsistemas.transita.data.local.converters.HorarioVeiculoListConverter
import br.com.texsistemas.transita.data.local.converters.RotaVeiculoPontoListConverter
import br.com.texsistemas.transita.data.web.dto.VeiculoDTO
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "veiculos")
@TypeConverters(HorarioVeiculoListConverter::class, RotaVeiculoPontoListConverter::class)
data class Veiculo(
    @PrimaryKey val id: String,
    val titulo: String,
    val placa: String,
    val tipoTransporte: String,
    val capacidade: Int,
    val ativo: Boolean,
    val dataCriacao: String? = null,
    @Embedded(prefix = "info_")
    val informacoesVeiculo: InformacoesVeiculo,
    val horarios: List<HorarioVeiculo> = emptyList(),
    val rotaVeiculoPonto: List<RotaVeiculoPonto> = emptyList()
)

@Serializable
data class InformacoesVeiculo(
    val latitude: Double,
    val longitude: Double,
    val dataRegistro: String,
    val dataRegistroProxPonto: String,
    val quantidadePassageiros: Int,
    val horarioAtualId: String
)

@Serializable
data class HorarioVeiculo(
    val id: String,
    val horarioId: String
)

@Serializable
data class RotaVeiculoPonto(
    val veiculoId: String,
    val horarioId: String,
    val pontoId: String,
    val ordem: Int
)

//toDomain
fun VeiculoDTO.toDomain(): Veiculo {
    return Veiculo(
        id = this.id,
        titulo = this.placa,
        placa = this.placa,
        tipoTransporte = this.tipoTransporte,
        capacidade = this.capacidade,
        ativo = this.ativo,
        dataCriacao = this.dataCriacao.toString(),
        informacoesVeiculo = InformacoesVeiculo(
            latitude = this.informacoesVeiculos.latitude,
            longitude = this.informacoesVeiculos.longitude,
            dataRegistro = this.informacoesVeiculos.dataRegistro.toString(),
            dataRegistroProxPonto = this.informacoesVeiculos.dataRegistroProxPonto.toString(),
            quantidadePassageiros = this.informacoesVeiculos.quantidadePassageiros,
            horarioAtualId = this.informacoesVeiculos.horarioAtualId
        ),
        horarios = this.horarios.map { horarioDTO ->
            HorarioVeiculo(
                id = horarioDTO.id,
                horarioId = horarioDTO.horarioId
            )
        },
        rotaVeiculoPonto = this.rotaVeiculoPonto.map { rotaDTO ->
            RotaVeiculoPonto(
                veiculoId = rotaDTO.veiculoId,
                horarioId = rotaDTO.horarioId,
                pontoId = rotaDTO.pontoId,
                ordem = rotaDTO.ordem
            )
        }
    )
}