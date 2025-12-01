package com.yaelteam.edulang.pantallas



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.viewmodels.DocentesViewModel
import com.yaelteam.edulang.viewmodels.EstudiantesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarDocente(navController: NavController) {

    val context = LocalContext.current
    val viewModel = remember { DocentesViewModel(context) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nuevo Docente") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            OutlinedTextField(
                value = viewModel.nombreCompleto,
                onValueChange = { viewModel.nombreCompleto = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = viewModel.asignatura,
                onValueChange = { viewModel.asignatura = it },
                label = { Text("Asignatura") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = viewModel.disponibilidad,
                onValueChange = { viewModel.disponibilidad = it },
                label = { Text("Disponibilidad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    viewModel.guardarDocente {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Docente")
            }
        }
    }
}