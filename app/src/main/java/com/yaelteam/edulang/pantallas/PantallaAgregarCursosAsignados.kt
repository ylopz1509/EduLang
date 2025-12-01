package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.datos.Curso
import com.yaelteam.edulang.datos.Docente
import com.yaelteam.edulang.viewmodels.CursosAsignadosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarCursosAsignados(nav: NavController) {
    val context = LocalContext.current
    val viewModel = remember { CursosAsignadosViewModel(context) }


    val docentes by viewModel.docentes.collectAsState(initial = emptyList())
    val cursos by viewModel.cursos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asignar Curso a Docente") },
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

            DropdownMenuDocentes(docentes) { sel ->
                viewModel.idDocente = sel.id.toString()
            }

            Spacer(Modifier.height(20.dp))


            DropdownMenuCursosAsignacion(cursos) { sel ->
                viewModel.idCurso = sel.id.toString()
            }

            Spacer(Modifier.height(30.dp))

            Button(

                onClick = {
                    viewModel.asignarCurso()
                    nav.popBackStack()
                },
                enabled = viewModel.idDocente.isNotBlank() && viewModel.idCurso.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Asignación")
            }
        }
    }
}


@Composable
fun DropdownMenuDocentes(
    docentes: List<Docente>,
    onSeleccion: (Docente) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    var seleccionado by remember {mutableStateOf("Selecciona un Docente") }

    Column {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) { Text(seleccionado) }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            docentes.forEach { docente ->
                DropdownMenuItem(
                    text = { Text("${docente.nombreCompleto} | Asignatura: ${docente.asignatura} | Disponibilidad: ${docente.disponibilidad}") },
                    onClick = {
                        seleccionado = docente.nombreCompleto
                        expanded = false
                        onSeleccion(docente)
                    }
                )
            }
        }
    }
}


@Composable
fun DropdownMenuCursosAsignacion(
    cursos: List<Curso>,
    onSeleccion: (Curso) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    var seleccionado by remember {mutableStateOf("Selecciona un Curso") }

    Column {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) { Text(seleccionado) }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cursos.forEach { curso ->
                DropdownMenuItem(
                    text = { Text("${curso.idioma} (${curso.nivel}) Horario: ${curso.horario}") },
                    onClick = {
                        seleccionado = "${curso.idioma} (${curso.nivel})"
                        expanded = false
                        onSeleccion(curso)
                    }
                )
            }
        }
    }
}