package com.yaelteam.edulang.pantallas


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.datos.Estudiante
import com.yaelteam.edulang.datos.Curso
import com.yaelteam.edulang.viewmodels.CursosViewModel
import com.yaelteam.edulang.viewmodels.EstudiantesViewModel
import com.yaelteam.edulang.viewmodels.PagosViewModel
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarPago(nav: NavController) {

    val context = LocalContext.current
    val viewModel = remember { PagosViewModel(context) }
    val vmEstudiantes = remember { EstudiantesViewModel(context) }
    val vmCursos = remember { CursosViewModel(context) }

    val estudiantes by vmEstudiantes.listaEstudiantes.collectAsState(initial = emptyList())
    val cursos by vmCursos.listaCursos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Pago") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            DropdownMenuEstudiantes(estudiantes) { est ->
                viewModel.idEstudiante = est.id.toString()
            }

            Spacer(Modifier.height(10.dp))


            DropdownMenuCursosPagos(cursos) { curso ->
                viewModel.idCurso = curso.id.toString()
            }

            Spacer(Modifier.height(10.dp))


            OutlinedTextField(
                value = viewModel.tipoPago,
                onValueChange = { viewModel.tipoPago = it },
                label = { Text("Tipo de pago (Inscripción / Mensualidad)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))


            OutlinedTextField(
                value = viewModel.monto,
                onValueChange = { viewModel.monto = it },
                label = { Text("Monto $") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))


            OutlinedTextField(
                value = viewModel.fecha,
                onValueChange = { viewModel.fecha = it },
                label = { Text("Fecha (DD/MM/AAAA)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.guardarPago {
                        nav.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Pago")
            }
        }
    }
}

@Composable
fun DropdownMenuEstudiantes(
    estudiantes: List<Estudiante>,
    onSeleccion: (Estudiante) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var seleccionado by remember { mutableStateOf("Selecciona un Estudiante") }

    Column {
        OutlinedButton(onClick = { expanded = true }, Modifier.fillMaxWidth()) {
            Text(seleccionado)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            estudiantes.forEach { est ->
                DropdownMenuItem(
                    text = { Text("${est.nombreCompleto} · Matrícula: ${est.matricula}") },
                    onClick = {
                        seleccionado = est.nombreCompleto
                        expanded = false
                        onSeleccion(est)
                    }
                )
            }
        }
    }
}

@Composable
fun DropdownMenuCursosPagos(
    cursos: List<Curso>,
    onSeleccion: (Curso) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var seleccionado by remember { mutableStateOf("Selecciona un Curso") }

    Column {
        OutlinedButton(onClick = { expanded = true }, Modifier.fillMaxWidth()) {
            Text(seleccionado)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            cursos.forEach { curso ->
                DropdownMenuItem(
                    text = { Text("${curso.idioma} nivel ${curso.nivel} · ${curso.horario}") },
                    onClick = {
                        seleccionado = curso.idioma
                        expanded = false
                        onSeleccion(curso)
                    }
                )
            }
        }
    }
}