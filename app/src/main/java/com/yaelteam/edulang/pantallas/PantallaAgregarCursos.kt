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
import com.yaelteam.edulang.viewmodels.CursosViewModel
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarCursos(navController: NavController){
    val context = LocalContext.current
    val viewModel = remember { CursosViewModel(context) }

    Scaffold (
        topBar = {
            TopAppBar(title = { Text("Nuevo Curso") })
        }
    ){ padding ->
        Column (
            modifier = Modifier.padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //Idioma
            OutlinedTextField(
                value = viewModel.idioma,
                onValueChange = {viewModel.idioma = it},
                label = {Text("Idioma")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            //Nivel
            OutlinedTextField(
                value = viewModel.nivel,
                onValueChange = {viewModel.nivel = it},
                label = {Text("Nivel")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            //Horario
            OutlinedTextField(
                value = viewModel.horario,
                onValueChange = {viewModel.horario = it},
                label = {Text("Horario")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            //Cupo
            OutlinedTextField(
                value = viewModel.cupo,
                onValueChange = {viewModel.cupo = it},
                label = {Text("Cupo")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Boton de Guardar
            Button(
                onClick = {
                    viewModel.guardarCurso {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Curso")
            }
        }
    }
}