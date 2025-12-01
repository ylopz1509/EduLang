package com.yaelteam.edulang.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

// Modelo para control de acceso
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreUsuario: String,
    val contrasena: String,
    val rol: String
)

// Modelo para los estudiantes
@Entity(tableName = "estudiantes")
data class Estudiante(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreCompleto: String,
    val matricula: String,
    val historialAcademico: String
)

//Modulo para los cursos
@Entity(tableName = "cursos")
data class Curso(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idioma: String,
    val horario: String,
    val nivel: String,
    val cupo: String
)
@Entity(tableName = "matriculas")
data class Matricula(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val matriculaEstudiante: String,
    val cursoId: Int,
    val fecha: String = ""
)

@Entity(tableName = "docentes")
data class Docente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreCompleto: String,
    val asignatura: String,
    val disponibilidad: String
)

@Entity(tableName = "cursos_asignados")
data class CursosAsignados(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idDocente: Int,
    val idCurso: Int
)

@Entity(tableName="pagos")
data class Pago(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idEstudiante: Int,
    val idCurso: Int,
    val tipo_pago: String,
    val monto: Double,
    val fecha: String
)