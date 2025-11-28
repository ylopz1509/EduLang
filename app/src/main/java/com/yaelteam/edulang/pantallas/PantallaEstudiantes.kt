package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.datos.Estudiante
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.viewmodels.EstudiantesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEstudiantes(navController: NavController) {
    val context = LocalContext.current
    val viewModel = remember { EstudiantesViewModel(context) }

    val listaReal by viewModel.listaEstudiantes.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Estudiantes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Rutas.AgregarEstudiante.ruta) }) {
                Icon(Icons.Default.Add, "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // BARRA PARA BUSCAR
            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { viewModel.textoBusqueda = it },
                label = { Text("Buscar estudiante...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA DE ESTUDIANTES
            if (listaReal.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron estudiantes")
                }
            } else {
                LazyColumn {
                    items(listaReal) { estudiante ->
                        ItemEstudiante(
                            estudiante = estudiante,
                            onDeleteClick = {
                                viewModel.eliminarEstudiante(estudiante)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemEstudiante(
    estudiante: Estudiante,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Datos del estudiante
            Column(modifier = Modifier.weight(1f)) {
                Text(text = estudiante.nombreCompleto, style = MaterialTheme.typography.titleMedium)
                Text(text = "Matrícula: ${estudiante.matricula}", color = Color.Black)
                Text(text = "Historial General: ${estudiante.historialAcademico}", style = MaterialTheme.typography.bodySmall)
            }

            // Botón Borrar
            Button(
                onClick = onDeleteClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Borrar")
            }
        }
    }
}