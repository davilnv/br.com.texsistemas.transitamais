package br.com.texsistemas.transita.data.local.converters

import androidx.room.TypeConverter
import br.com.texsistemas.transita.domain.model.RotaVeiculoPonto
import kotlinx.serialization.json.Json

class RotaVeiculoPontoListConverter {
    @TypeConverter
    fun fromHorarioList(value: List<RotaVeiculoPonto>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toHorarioList(value: String): List<RotaVeiculoPonto> {
        return Json.decodeFromString(value)
    }
}