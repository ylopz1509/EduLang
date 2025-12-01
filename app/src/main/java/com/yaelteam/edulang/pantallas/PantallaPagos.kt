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
import com.yaelteam.edulang.datos.Pago
import com.yaelteam.edulang.navegacion.Rutas
import com.yaelteam.edulang.viewmodels.CursosViewModel
import com.yaelteam.edulang.viewmodels.EstudiantesViewModel
import com.yaelteam.edulang.viewmodels.PagosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPagos(navController: NavController) {

    val context = LocalContext.current
    val pagosVM = remember { PagosViewModel(context) }
    val estudiantesVM = remember { EstudiantesViewModel(context) }
    val cursosVM = remember { CursosViewModel(context) }

    val listaPagos by pagosVM.listaPagos.collectAsState(initial = emptyList())
    val listaEstudiantes by estudiantesVM.listaEstudiantes.collectAsState(initial = emptyList())
    val listaCursos by cursosVM.listaCursos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar( title = { Text("Registro de Pagos") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Regresar") } } ) },
        floatingActionButton = { FloatingActionButton( onClick = { navController.navigate(Rutas.AgregarPago.ruta) } ) { Icon(Icons.Default.Add, "Agregar Pago") } } ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {


            val pagosFiltrados = listaPagos.filter { pago ->
                val estudiante = listaEstudiantes.find { it.id == pago.idEstudiante }
                val curso = listaCursos.find { it.id == pago.idCurso }
                val texto = pagosVM.textoBusqueda.lowercase()
                (estudiante?.nombreCompleto?.lowercase()?.contains(texto) == true) ||
                        (curso?.idioma?.lowercase()?.contains(texto) == true)
            }

            if (pagosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay pagos registrados todavía")
                }
            } else {
                LazyColumn {
                    items(pagosFiltrados) { pago ->
                        val estudiante = listaEstudiantes.find { it.id == pago.idEstudiante }
                        val curso = listaCursos.find { it.id == pago.idCurso }

                        ItemPago(
                            estudianteNombre = estudiante?.nombreCompleto ?: "Desconocido",
                            estudianteMatricula = estudiante?.matricula ?: "Desconocida",
                            cursoNombre = curso?.idioma ?: "Desconocido",
                            pago = pago,
                            onDeleteClick = { pagosVM.eliminarPago(pago) }
                        )
                    }
                }
            }}}}

@Composable
fun ItemPago(
    estudianteNombre: String,
    estudianteMatricula: String,
    cursoNombre: String,
    pago: Pago,
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
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Estudiante: $estudianteNombre (Matrícula: $estudianteMatricula)")
                Text("Curso: $cursoNombre")
                Text("Tipo de pago: ${pago.tipo_pago}")
                Text("Fecha: ${pago.fecha}")
                Text("Monto: $${pago.monto}")
            }
            Button(
                onClick = onDeleteClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) { Text("Eliminar") }
        }
    }
}
