package br.com.texsistemas.transita.data.repository

import android.annotation.SuppressLint
import android.content.Context
import br.com.texsistemas.transita.domain.model.UsuarioPosicao
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class UsuarioLocalizacaoRepositoryImpl(private val context: Context) :
    UsuarioLocalizacaoRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getLocalizacaoAtual(): UsuarioPosicao? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val localizacao = fusedLocationClient.lastLocation.await()
        return localizacao?.let { UsuarioPosicao(it.latitude, it.longitude) }
    }

}