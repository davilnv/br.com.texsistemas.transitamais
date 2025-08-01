package br.com.texsistemas.transita.core.common.utils

/**
 * Utilitário para manipulação de horários.
 * Função auxiliar para converter "HH:mm" para minutos desde a meia-noite
 */
fun horarioParaMinutos(horarioFormatado: String): Int {
    return try {
        val partes = horarioFormatado.split(":")
        val horas = partes.getOrNull(0)?.toIntOrNull() ?: 0
        val minutos = partes.getOrNull(1)?.toIntOrNull() ?: 0
        horas * 60 + minutos
    } catch (_: Exception) {
        Int.MAX_VALUE
    }
}