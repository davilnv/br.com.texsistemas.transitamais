package br.com.texsistemas.transita.data.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(
    val email: String,
    val senha: String
)
