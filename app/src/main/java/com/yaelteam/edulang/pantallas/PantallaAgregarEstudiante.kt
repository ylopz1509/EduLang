package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.viewmodels.EstudiantesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarEstudiante(navController: NavController) {

    val context = LocalContext.current
    val viewModel = remember { EstudiantesViewModel(context) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nuevo Estudiante") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Nombre
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.nombre = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Matricula
            OutlinedTextField(
                value = viewModel.matricula,
                onValueChange = { viewModel.matricula = it },
                label = { Text("Matrícula") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Historial
            OutlinedTextField(
                value = viewModel.historial,
                onValueChange = { viewModel.historial = it },
                label = { Text("Datos Académicos (Calificación General)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Btn Guardar
            Button(
                onClick = {
                    viewModel.guardarEstudiante {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Estudiante")
            }
        }
    }
}