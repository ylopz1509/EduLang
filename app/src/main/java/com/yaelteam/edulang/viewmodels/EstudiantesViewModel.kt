package com.yaelteam.edulang.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaelteam.edulang.datos.BaseDeDatosEduLang
import com.yaelteam.edulang.datos.Estudiante
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class EstudiantesViewModel(context: Context) : ViewModel() {

    private val dao = BaseDeDatosEduLang.obtenerBaseDatos(context).estudianteDao()
    var textoBusqueda by mutableStateOf("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val listaEstudiantes: Flow<List<Estudiante>> = snapshotFlow { textoBusqueda }
        .flatMapLatest { consulta ->
            if (consulta.isEmpty()) {
                dao.obtenerTodos()
            } else {
                dao.buscarEstudiante(consulta)
            }
        }

    // Variables para agregar alumnos
    var nombre by mutableStateOf("")
    var matricula by mutableStateOf("")
    var historial by mutableStateOf("")

    fun guardarEstudiante(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val nuevo = Estudiante(
                nombreCompleto = nombre,
                matricula = matricula,
                historialAcademico = historial
            )
            dao.insertarEstudiante(nuevo)
            nombre = ""; matricula = ""; historial = ""
            onSuccess()
        }
    }

    // Eliminar wey
    fun eliminarEstudiante(estudiante: Estudiante) {
        viewModelScope.launch {
            dao.eliminarEstudiante(estudiante)
        }
    }
}