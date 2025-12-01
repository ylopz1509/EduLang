package com.yaelteam.edulang.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    //Hacemos las funciones para las consultas
    @Insert
    suspend fun insertarCurso(curso: Curso)

    @Query("SELECT * FROM cursos")
    fun obtenerTodo(): Flow<List<Curso>>

    @Query("SELECT * FROM cursos WHERE idioma LIKE '%' || :consulta || '%' OR nivel LIKE '%' || :consulta || '%'")
    fun buscarCurso(consulta: String): Flow<List<Curso>>

    @Delete
    suspend fun eliminarCurso(curso: Curso)
}