package br.com.texsistemas.transita.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.texsistemas.transita.domain.model.Veiculo

@Dao
interface VeiculoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVeiculo(veiculos: List<Veiculo>)

    @Query("SELECT * FROM veiculos")
    suspend fun getAllVeiculo(): List<Veiculo>

    @Query("SELECT * FROM veiculos WHERE id = :pontoId")
    suspend fun getVeiculoById(pontoId: String): Veiculo?

    @Query("SELECT * FROM veiculos WHERE id IN (:pontoIds)")
    suspend fun getVeiculoByIds(pontoIds: List<String>): List<Veiculo>
}