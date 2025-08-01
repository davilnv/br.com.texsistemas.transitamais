package br.com.texsistemas.transita.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioPosicao(
    val latitude: Double,
    val longitude: Double
)