package br.com.texsistemas.transita.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.texsistemas.transita.domain.model.PontoOnibus

@Dao
interface PontoOnibusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPontosOnibus(pontos: List<PontoOnibus>)

    @Query("SELECT * FROM pontos_onibus")
    suspend fun getAllPontosOnibus(): List<PontoOnibus>

    @Query("SELECT * FROM pontos_onibus WHERE id = :pontoId")
    suspend fun getPontoOnibusById(pontoId: String): PontoOnibus?

    @Query("UPDATE pontos_onibus SET imagemLocal = :imagemLocal WHERE id = :pontoId")
    suspend fun updateImagemLocal(pontoId: String, imagemLocal: String)

    @Query("SELECT * FROM pontos_onibus WHERE id IN (:pontoIds)")
    suspend fun getPontosOnibusByIds(pontoIds: List<String>): List<PontoOnibus>
}