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