package br.com.texsistemas.transita.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.texsistemas.transita.data.local.converters.HorarioConverter
import br.com.texsistemas.transita.data.local.converters.HorarioListConverter
import br.com.texsistemas.transita.data.local.converters.HorarioVeiculoListConverter
import br.com.texsistemas.transita.data.local.converters.RotaVeiculoPontoListConverter
import br.com.texsistemas.transita.data.local.dao.PontoOnibusDao
import br.com.texsistemas.transita.data.local.dao.VeiculoDao
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.Veiculo

@Database(entities = [PontoOnibus::class, Veiculo::class], version = 3, exportSchema = false)
@TypeConverters(
    HorarioListConverter::class,
    HorarioConverter::class,
    HorarioVeiculoListConverter::class,
    RotaVeiculoPontoListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pontoOnibusDao(): PontoOnibusDao
    abstract fun veiculoDao(): VeiculoDao
}