package com.yaelteam.edulang.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EstudianteDao {
    @Insert
    suspend fun insertarEstudiante(estudiante: Estudiante)

    @Query("SELECT * FROM estudiantes")
    fun obtenerTodos(): Flow<List<Estudiante>>
    // Los signos % significan "cualquier texto antes o después
    @Query("SELECT * FROM estudiantes WHERE nombreCompleto LIKE '%' || :consulta || '%' OR matricula LIKE '%' || :consulta || '%'")
    fun buscarEstudiante(consulta: String): Flow<List<Estudiante>>

    // Eliminar
    @Delete
    suspend fun eliminarEstudiante(estudiante: Estudiante)

    @Query("SELECT * FROM estudiantes WHERE matricula = :matricula LIMIT 1")
    suspend fun obtenerPorMatriculaDirecta(matricula: String): Estudiante?

}