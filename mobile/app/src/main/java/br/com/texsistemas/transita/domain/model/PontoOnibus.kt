package br.com.texsistemas.transita.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.texsistemas.transita.data.local.converters.HorarioListConverter
import br.com.texsistemas.transita.data.local.converters.HorarioConverter
import br.com.texsistemas.transita.data.web.dto.PontoOnibusDTO
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "pontos_onibus")
@TypeConverters(HorarioListConverter::class, HorarioConverter::class)
data class PontoOnibus(
    @PrimaryKey val id: String,
    val titulo: String,
    val descricao: String = "",
    val avaliacao: Float = 0f,
    val informacao: String = "",
    @Embedded(prefix = "endereco_")
    val endereco: Endereco,
    val latitude: Double,
    val longitude: Double,
    @Embedded(prefix = "marcador_")
    val marcadorMapa: MarcadorMapa? = null,
    val horarios: List<Horario> = emptyList(),
    val tipoPonto: String,
    val linkImagem: String? = null,
    val proximoHorario: Horario? = null,
    val imagemLocal: String? = null
)

@Serializable
data class Endereco(
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    val cep: String
) {
    override fun toString(): String {
        return "$rua, $numero - $bairro, $cidade - $uf, $cep"
    }
}

@Serializable
data class Horario(
    val id: String,
    val horario: String,
    val tipoPonto: String
) {
    val horarioFormatado: String
        get() = horario.substring(0, 5)
}

//toDomain
fun PontoOnibusDTO.toDomain(): PontoOnibus {
    return PontoOnibus(
        id = this.id,
        titulo = this.titulo,
        descricao = this.descricao,
        avaliacao = this.avaliacao.toFloat(),
        informacao = this.informacao,
        endereco = Endereco(
//            id = this.endereco.id,
            rua = this.endereco.rua,
            numero = this.endereco.numero,
            bairro = this.endereco.bairro,
            cidade = this.endereco.cidade,
            uf = this.endereco.uf,
            cep = this.endereco.cep
        ),
        latitude = this.latitude,
        longitude = this.longitude,
        marcadorMapa = MarcadorMapa(
            latitude = this.latitude,
            longitude = this.longitude,
            titulo = this.titulo
        ),
        horarios = this.horarios.map { horarioDTO ->
            Horario(
                id = horarioDTO.id,
                horario = horarioDTO.horario,
                tipoPonto = horarioDTO.tipoPonto
            )
        },
        tipoPonto = this.tipoPonto,
        linkImagem = this.linkImagem
    )
}