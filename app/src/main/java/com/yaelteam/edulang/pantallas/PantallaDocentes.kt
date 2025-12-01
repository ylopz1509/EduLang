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
import com.yaelteam.edulang.datos.Docente
import com.yaelteam.edulang.datos.Estudiante
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.viewmodels.DocentesViewModel
import com.yaelteam.edulang.viewmodels.EstudiantesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDocentes(navController: NavController) {
    val context = LocalContext.current
    val viewModel = remember { DocentesViewModel(context) }

    val listaReal by viewModel.listaDocentes.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Docentes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Rutas.AgregarDocente.ruta) }) {
                Icon(Icons.Default.Add, "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { viewModel.textoBusqueda = it },
                label = { Text("Buscar docente...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))


            if (listaReal.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron docentes")
                }
            } else {
                LazyColumn {
                    items(listaReal) { docente ->
                        ItemDocente(
                            docente = docente,
                            onDeleteClick = {
                                viewModel.eliminarDocente(docente)
                            }

                        )
                    }
                }
            }
        }
    }}

@Composable
fun ItemDocente(
    docente: Docente,
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
                Text(text = docente.nombreCompleto, style = MaterialTheme.typography.titleMedium)
                Text(text = "Asignatura: ${docente.asignatura}", color = Color.Black)
                Text(
                    text = "Disponibilidad: ${docente.disponibilidad}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column {

                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Borrar")
                }
            }
        }
    }
}