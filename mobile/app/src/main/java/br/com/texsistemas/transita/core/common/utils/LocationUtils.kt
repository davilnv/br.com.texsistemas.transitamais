package br.com.texsistemas.transita.core.common.utils

import kotlin.math.*

private const val EARTH_RADIUS_KM = 6371.0 // Raio da Terra em quilômetros

fun calcularDistanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val lat1Rad = Math.toRadians(lat1)
    val lat2Rad = Math.toRadians(lat2)

    val a = sin(dLat / 2).pow(2) + sin(dLon / 2).pow(2) * cos(lat1Rad) * cos(lat2Rad)
    val c = 2 * asin(sqrt(a))

    return EARTH_RADIUS_KM * c
}