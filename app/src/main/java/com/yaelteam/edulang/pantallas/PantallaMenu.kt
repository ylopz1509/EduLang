package com.yaelteam.edulang.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaelteam.edulang.navegacion.Rutas

@Composable
fun PantallaMenuPrincipal(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Panel de Administración", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        // Btn modulo Estudiantes
        BotonMenu(text = "Estudiantes", onClick = { navController.navigate(Rutas.GestionEstudiantes.ruta) })

        // Btn modulo Docentes
        BotonMenu(text = "Docentes", onClick = { navController.navigate(Rutas.GestionDocentes.ruta) })
        BotonMenu(text = "Cursos", onClick = { navController.navigate(Rutas.GestionCursos.ruta) })
        BotonMenu(text = "Asignacion Docentes-Cursos", onClick = { navController.navigate(Rutas.GestionCursosDocentes.ruta) })
        // Btn modulo Matrículas
        BotonMenu(text = "Matrículas", onClick = { navController.navigate(Rutas.GestionMatriculas.ruta) })

        // Btn modulo Cursos


        //Btn modulo Pagos
        BotonMenu(text = "Pagos", onClick = { navController.navigate(Rutas.GestionPagos.ruta)})

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedButton(onClick = {
            navController.navigate(Rutas.Login.ruta) {
                popUpTo(0)
            }
        }) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun BotonMenu(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(50.dp)
    ) {
        Text(text)
    }
}