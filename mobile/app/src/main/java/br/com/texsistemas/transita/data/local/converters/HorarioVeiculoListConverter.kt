package br.com.texsistemas.transita.data.local.converters

import androidx.room.TypeConverter
import br.com.texsistemas.transita.domain.model.HorarioVeiculo
import kotlinx.serialization.json.Json

class HorarioVeiculoListConverter {
    @TypeConverter
    fun fromHorarioList(value: List<HorarioVeiculo>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toHorarioList(value: String): List<HorarioVeiculo> {
        return Json.decodeFromString(value)
    }
}