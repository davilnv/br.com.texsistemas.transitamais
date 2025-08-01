package br.com.texsistemas.transita.data.web.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MensagemDTO(
    val codigo: Int?,
    val mensagem: String,
    @Transient val sucesso: Boolean = codigo == 200,
    @Transient val dataObject: Any? = null
)