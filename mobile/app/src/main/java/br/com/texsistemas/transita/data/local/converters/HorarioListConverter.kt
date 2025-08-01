package br.com.texsistemas.transita.data.local.converters

import androidx.room.TypeConverter
import br.com.texsistemas.transita.domain.model.Horario
import kotlinx.serialization.json.Json

class HorarioListConverter {
    @TypeConverter
    fun fromHorarioList(value: List<Horario>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toHorarioList(value: String): List<Horario> {
        return Json.decodeFromString(value)
    }
}