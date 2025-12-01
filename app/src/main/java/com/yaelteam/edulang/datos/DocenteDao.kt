package com.yaelteam.edulang.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DocenteDao {

    @Insert
    suspend fun insertarDocente(docente: Docente)

    @Query("SELECT * FROM docentes ORDER BY nombreCompleto ASC")
    fun obtenerTodosLosDocentes(): Flow<List<Docente>>

    @Query("SELECT * FROM docentes WHERE nombreCompleto LIKE '%' || :texto || '%'")
    fun buscarDocente(texto: String): Flow<List<Docente>>

    @Delete
    suspend fun eliminarDocente(docente: Docente)
}