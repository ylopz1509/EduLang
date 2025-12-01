package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.datos.Curso
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.viewmodels.CursosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCursos(navController: NavController){
    val context = LocalContext.current
    val viewModel = remember { CursosViewModel(context) }

    val listaPrincipal by viewModel.listaCursos.collectAsState(initial = emptyList())

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Cursos") },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.navigate(Rutas.AgregarCursos.ruta)}) {
                Icon(Icons.Default.Add, "Agregar")
            }
        }
    ){ padding ->
        Column (
            modifier = Modifier.padding(padding)
                .padding(16.dp)
        ){
            //Barra de busqueda
            OutlinedTextField(
                value = viewModel.buscar,
                onValueChange = {viewModel.buscar = it},
                label = {Text("Buscar Curso")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Lista de cursos
            if(listaPrincipal.isEmpty()){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text("No se encontraron cursos")
                }
            } else {
                LazyColumn {
                    items(listaPrincipal) { curso ->
                        ItemCurso(
                            curso = curso,
                            onDeleteClick = {
                                viewModel.eliminarCurso(curso)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCurso(
    curso: Curso,
    onDeleteClick: () -> Unit
){
    Card (
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Row (
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            //Datos del curso
            Column (modifier = Modifier.weight(1f)){
                Text(text = curso.idioma, style = MaterialTheme.typography.titleMedium)
                Text(text = "Nivel: ${curso.nivel}", color = Color.Black)
                Text(text = "Horario: ${curso.horario}", color = Color.Black)
                Text(text = "Cupo: ${curso.cupo}", color = Color.Black)
            }

            //Boton de borrar
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