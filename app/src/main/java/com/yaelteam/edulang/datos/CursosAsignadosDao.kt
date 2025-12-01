package com.yaelteam.edulang.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CursosAsignadosDao {

    @Insert
    suspend fun asignarCurso(relacion: CursosAsignados)

    @Query("""
        SELECT 
            doc.nombreCompleto AS nombreDocente, 
            cur.idioma AS idiomaCurso,
            cur.nivel AS nivelCurso,
            cur.horario AS horarioCurso
        FROM cursos_asignados AS rel
        INNER JOIN docentes AS doc ON doc.id = rel.idDocente
        INNER JOIN cursos AS cur ON cur.id = rel.idCurso
    """)

    fun obtenerAsignacionesDetalle(): Flow<List<AsignacionDetalle>>

    @Query("DELETE FROM cursos_asignados WHERE id = :id")
    suspend fun eliminarAsignacion(id: Int)

}