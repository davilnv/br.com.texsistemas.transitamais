package br.com.texsistemas.transita.data.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenDTO(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshExpiresIn: Int,
    val scope: String
)
