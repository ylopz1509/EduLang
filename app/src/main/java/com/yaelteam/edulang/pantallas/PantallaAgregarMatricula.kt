package com.yaelteam.edulang.pantallas


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.viewmodels.MatriculasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarMatriculas(navController: NavController) {
    val context = LocalContext.current
    val viewModel = remember { MatriculasViewModel(context) }

    val listaCursos by viewModel.listaCursos.collectAsState(initial = emptyList())

    var expandir by remember { mutableStateOf(false) }

    var errorMensaje by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Módulo Matriculas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->

        if (errorMensaje != null) {
            AlertDialog(
                onDismissRequest = { errorMensaje = null },
                title = { Text("Error") },
                text = { Text(errorMensaje!!) },
                confirmButton = {
                    TextButton(onClick = { errorMensaje = null }) {
                        Text("OK")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.matriculaInput,
                onValueChange = { viewModel.matriculaInput = it },
                label = { Text("Matrícula del estudiante") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.cursoSeleccionadoTexto,
                onValueChange = {},
                label = { Text("Seleccionar curso") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandir = true },
                enabled = false,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
            )

            DropdownMenu(
                expanded = expandir,
                onDismissRequest = { expandir = false }
            ) {
                if (listaCursos.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("No hay cursos registrados") },
                        onClick = { expandir = false }
                    )
                } else {
                    listaCursos.forEach { curso ->
                        DropdownMenuItem(
                            text = { Text("${curso.idioma} ${curso.nivel} - ${curso.horario}") },
                            onClick = {
                                viewModel.cursoSeleccionadoId = curso.id
                                viewModel.cursoSeleccionadoTexto = "${curso.idioma} ${curso.nivel}"
                                expandir = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.inscribirCurso(
                        onSuccess = {
                            Toast.makeText(context, "Inscripción guardada", Toast.LENGTH_SHORT).show()
                        },
                        onError = { msg ->

                            errorMensaje = msg
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Inscribir")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate(Rutas.HistorialMatriculas.ruta) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Historial de Matrículas")
            }
        }
    }
}
