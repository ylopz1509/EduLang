package com.yaelteam.edulang.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MatriculaDao {
    @Insert
    suspend fun insertarMatricula(matricula: Matricula)

    @Query("SELECT * FROM matriculas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Matricula>>

    @Query("SELECT * FROM matriculas WHERE matriculaEstudiante = :matricula ORDER BY id DESC")
    fun obtenerPorMatricula(matricula: String): Flow<List<Matricula>>
}