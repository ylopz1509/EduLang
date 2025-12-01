package com.yaelteam.edulang.navegacion

sealed class Rutas(val ruta: String) {
    object Login : Rutas("login")
    object MenuPrincipal : Rutas("menu_principal")

    // modulos listos
    object GestionEstudiantes : Rutas("gestion_estudiantes")
    object DetalleEstudiante : Rutas("detalle_estudiante")
    object AgregarEstudiante : Rutas("agregar_estudiante")

    // Modulos faltantes
    object GestionDocentes : Rutas("gestion_docentes")
    object AgregarDocente : Rutas("agregar_docente")
    object GestionCursosDocentes : Rutas("gestion_cursos_docentes")
    object AgregarCursosDocentes : Rutas("agregar_cursos_docentes")
    object GestionMatriculas : Rutas("gestion_matrículas")
    object GestionCursos : Rutas("gestion_cursos")

    object AgregarCursos : Rutas ("agregar_cursos")

    object HistorialMatriculas : Rutas("historial_matriculas")

    object AgregarMatricula : Rutas("agregar_matricula")
    object GestionPagos: Rutas("gestion_pagos")
    object AgregarPago : Rutas("agregar_pago")
}