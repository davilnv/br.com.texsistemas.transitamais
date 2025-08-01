package br.com.texsistemas.transita.data.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class PontoOnibusDTO(
    val id: String,
    val titulo: String,
    val descricao: String,
    val avaliacao: Double,
    val informacao: String,
    val endereco: EnderecoDTO,
    val latitude: Double,
    val longitude: Double,
    val horarios: List<HorarioDTO>,
    val tipoPonto: String,
    val linkImagem: String?
)

@Serializable
data class EnderecoDTO(
    val id: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    val cep: String
)

@Serializable
data class HorarioDTO(
    val id: String,
    val horario: String,
    val tipoPonto: String
)