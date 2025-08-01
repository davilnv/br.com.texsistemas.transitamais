package br.com.texsistemas.transita.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MarcadorMapa(
    val titulo: String,
    val latitude: Double,
    val longitude: Double
)