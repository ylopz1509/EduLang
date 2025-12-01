package com.yaelteam.edulang.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaelteam.edulang.datos.AsignacionDetalle
import com.yaelteam.edulang.datos.BaseDeDatosEduLang
import com.yaelteam.edulang.datos.CursosAsignados
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CursosAsignadosViewModel(context: Context) : ViewModel() {

    private val db = BaseDeDatosEduLang.obtenerBaseDatos(context)
    private val daoDocentes = db.docenteDao()
    private val daoCursos = db.cursoDao()
    private val daoRel = db.cursosAsignadosDao()
    //val asignaciones = mutableStateListOf<Asignacion>()


    var idDocente by mutableStateOf("")
    var idCurso by mutableStateOf("")
    var textoBusqueda by mutableStateOf("")

    val docentes = daoDocentes.obtenerTodosLosDocentes()
    val cursos = daoCursos.obtenerTodo()
    val asignacionesDetalle: StateFlow<List<AsignacionDetalle>> =
        daoRel.obtenerAsignacionesDetalle()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    fun asignarCurso() = viewModelScope.launch {
        daoRel.asignarCurso(
            CursosAsignados(
                idDocente = idDocente.toInt(),
                idCurso = idCurso.toInt()
            )
        )
        idDocente = ""
        idCurso = ""
    }
}