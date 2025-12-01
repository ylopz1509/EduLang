package com.yaelteam.edulang.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaelteam.edulang.datos.BaseDeDatosEduLang
import com.yaelteam.edulang.datos.Docente
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class DocentesViewModel(context: Context) : ViewModel() {

    private val dao = BaseDeDatosEduLang.obtenerBaseDatos(context).docenteDao()
    var textoBusqueda by mutableStateOf("")
    val listaDocentes: Flow<List<Docente>> = snapshotFlow { textoBusqueda }
        .flatMapLatest { consulta ->
            if (consulta.isEmpty()) {
                dao.obtenerTodosLosDocentes()
            } else {
                dao.buscarDocente(consulta)
            }
        }


    var nombreCompleto by mutableStateOf("")
    var asignatura by mutableStateOf("")
    var disponibilidad by mutableStateOf("")


    fun guardarDocente(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val docente = Docente(
                nombreCompleto = nombreCompleto,
                asignatura = asignatura,
                disponibilidad = disponibilidad
            )
            dao.insertarDocente(docente)

            nombreCompleto = ""
            asignatura = ""
            disponibilidad = ""
            onSuccess()
        }
    }


    fun eliminarDocente(docente: Docente) {
        viewModelScope.launch {
            dao.eliminarDocente(docente)
        }
    }
}