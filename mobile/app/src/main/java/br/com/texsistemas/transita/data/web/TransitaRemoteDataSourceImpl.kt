package br.com.texsistemas.transita.data.web

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import br.com.texsistemas.transita.core.common.baseUrl
import br.com.texsistemas.transita.core.network.KtorHttpClient
import br.com.texsistemas.transita.data.web.dto.AuthTokenDTO
import br.com.texsistemas.transita.data.web.dto.LoginRequestDTO
import br.com.texsistemas.transita.data.web.dto.MensagemDTO
import br.com.texsistemas.transita.data.web.dto.PontoOnibusDTO
import br.com.texsistemas.transita.data.web.serializer.LocalDateTimeSerializer
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
private val json = Json {
    serializersModule = SerializersModule {
        contextual(LocalDateTime::class, LocalDateTimeSerializer)
    }
}

class TransitaRemoteDataSourceImpl : TransitaRemoteDataSource {
    override suspend fun postLogin(loginRequest: LoginRequestDTO): Result<MensagemDTO> = try {
        val response: HttpResponse =
            KtorHttpClient.httpClient.post("$baseUrl/usuarios/public/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }

        if (response.status == HttpStatusCode.OK) {
            val usuario: AuthTokenDTO = response.body()
            Result.success(
                MensagemDTO(
                    codigo = response.status.value,
                    mensagem = "Login realizado com sucesso",
                    sucesso = true,
                    dataObject = usuario
                )
            )
        } else {
            val errorBody = response.bodyAsText()
            val errorResponse = json.decodeFromString<MensagemDTO>(errorBody)
            Log.d("TransitaRemoteDataSourceImpl", "error body: $errorBody")
            Log.d("TransitaRemoteDataSourceImpl", "error response: $errorResponse")
            Result.success(
                MensagemDTO(
                    codigo = errorResponse.codigo,
                    mensagem = errorResponse.mensagem,
                    sucesso = false
                )
            )
        }
    } catch (e: Exception) {
        Log.d("TransitaRemoteDataSourceImpl", "error: $e")
        Result.failure(e)
    }

    override suspend fun getAllPontosOnibusProximos(
        latitudeParameter: Double,
        longitudeParameter: Double,
        raio: Double
    ): Result<List<PontoOnibusDTO>> = try {
        val response: HttpResponse = KtorHttpClient.httpClient.get("$baseUrl/pontos/public") {
            parameter("latitude", latitudeParameter)
            parameter("longitude", longitudeParameter)
            parameter("distanciaKm", raio) // Raio de 1 km
            contentType(ContentType.Application.Json)
        }

        if (response.status == HttpStatusCode.OK) {
            val pontos: List<PontoOnibusDTO> = response.body()
            Result.success(pontos)
        } else {
            val errorBody = response.bodyAsText()
            Log.d("TransitaRemoteDataSourceImpl", "error body: $errorBody")
            Result.failure(Exception("Erro ao buscar pontos de ônibus"))
        }
    } catch (e: Exception) {
        Log.d("TransitaRemoteDataSourceImpl", "error: $e")
        Result.failure(e)
    }
}