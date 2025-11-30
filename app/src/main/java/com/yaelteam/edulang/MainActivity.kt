package com.yaelteam.edulang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.pantallas.PantallaAgregarEstudiante
import com.yaelteam.edulang.pantallas.PantallaCursos
import com.yaelteam.edulang.pantallas.PantallaDocentes
import com.yaelteam.edulang.pantallas.PantallaEstudiantes
import com.yaelteam.edulang.pantallas.PantallaLogin
import com.yaelteam.edulang.pantallas.PantallaMatriculas
import com.yaelteam.edulang.pantallas.PantallaMenuPrincipal
import com.yaelteam.edulang.pantallas.PantallaPagos


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NavegacionApp()
            }
        }
    }
}

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.Login.ruta
    ) {
        // Control de Acceso (ya)
        composable(Rutas.Login.ruta) {
            PantallaLogin(navController)
        }

        // Menú Principal (ya)
        composable(Rutas.MenuPrincipal.ruta) {
            PantallaMenuPrincipal(navController)
        }

        // Gestión estudiantes (ya)
        composable(Rutas.GestionEstudiantes.ruta) {
            PantallaEstudiantes(navController)
        }

        // Modulos faltantes.-
        // modificar el contenido de las funciones
        composable(Rutas.GestionDocentes.ruta) {
            PantallaDocentes()
        }
        composable(Rutas.GestionMatriculas.ruta) {
            PantallaMatriculas()
        }
        composable(Rutas.GestionCursos.ruta) {
            PantallaCursos()
        }
        composable (Rutas.GestionPagos.ruta){
            PantallaPagos()
        }
        composable(Rutas.GestionEstudiantes.ruta) {
            PantallaEstudiantes(navController)
        }
        composable(Rutas.AgregarEstudiante.ruta) {
            PantallaAgregarEstudiante(navController)
        }
    }
}