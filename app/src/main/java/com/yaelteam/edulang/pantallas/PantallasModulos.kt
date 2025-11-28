package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// (Docentes)
// borrar la funcion y hagan un archivo nuevo de que 'PantallaDocentes'
@Composable
fun PantallaDocentes() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Módulo de Docentes (Ingenieras trabajando)")
    }
}

// (Cursos)
@Composable
fun PantallaCursos() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Módulo de Cursos (Ingenieras trabajando)")
    }
}

// Pagos y Matrículas)
@Composable
fun PantallaPagos() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Módulo de Pagos (Ingenieras trabajando)")
    }
}