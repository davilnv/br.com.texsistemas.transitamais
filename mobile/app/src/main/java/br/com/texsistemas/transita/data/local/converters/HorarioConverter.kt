package br.com.texsistemas.transita.data.local.converters

import androidx.room.TypeConverter
import br.com.texsistemas.transita.domain.model.Horario
import kotlinx.serialization.json.Json

class HorarioConverter {
    @TypeConverter
    fun fromHorario(horario: Horario?): String? {
        return horario?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toHorario(value: String?): Horario? {
        return value?.let { Json.decodeFromString<Horario>(it) }
    }
}

