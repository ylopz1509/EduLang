package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.datos.AsignacionDetalle
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.viewmodels.CursosAsignadosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCursosAsignados(navController: NavController) {

    val context = LocalContext.current
    // No pasamos idDocente, ya que esta es la pantalla de listado
    val viewModel = remember { CursosAsignadosViewModel(context) }

    val asignacionesDetalle by viewModel.asignacionesDetalle.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asignaciones Docentes-Cursos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Rutas.AgregarCursosDocentes.ruta) } // Usarías tu ruta
            ) {
                Icon(Icons.Default.Add, "Agregar Asignación")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {

            // LISTA DE ASIGNACIONES DETALLE
            if (asignacionesDetalle.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay cursos asignados todavía.")
                }
            } else {
                Text(
                    text = "Cursos Asignados Actualmente:",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                ListaAsignaciones(asignacionesDetalle)
            }
        }
    }
}

@Composable
fun ListaAsignaciones(asignaciones: List<AsignacionDetalle>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Llenar el resto de la pantalla
    ) {
        items(asignaciones) { asignacion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    // Nombre del Docente en negrita para resaltarlo
                    Text(
                        text = "Docente: ${asignacion.nombreDocente}",
                        fontWeight = FontWeight.Bold
                    )
                    // Detalles del Curso con sangría
                    Text(
                        text = "Curso: ${asignacion.idiomaCurso} - ${asignacion.nivelCurso}",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "Horario: ${asignacion.horarioCurso}",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}