package com.yaelteam.edulang.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaelteam.edulang.datos.BaseDeDatosEduLang
import com.yaelteam.edulang.datos.Curso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class CursosViewModel(context: Context): ViewModel() {

    private val cursosdao = BaseDeDatosEduLang.obtenerBaseDatos(context).cursoDao()

    var buscar by mutableStateOf("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val listaCursos: Flow<List<Curso>> = snapshotFlow { buscar }
        .flatMapLatest { consulta ->
            if (consulta.isEmpty()){
                cursosdao.obtenerTodo()
            } else {
                cursosdao.buscarCurso(consulta)
            }
        }

    var idioma by mutableStateOf("")
    var horario by mutableStateOf("")
    var nivel by mutableStateOf("")
    var cupo by mutableStateOf("")

    fun guardarCurso(onSuccess: () -> Unit){
        viewModelScope.launch {
            val nuevo = Curso(
                idioma = idioma,
                horario = horario,
                nivel = nivel,
                cupo = cupo
            )
            cursosdao.insertarCurso(nuevo)
            idioma = ""; horario = ""; nivel = ""; cupo = ""
            onSuccess()
        }
    }

    fun eliminarCurso(curso: Curso){
        viewModelScope.launch {
            cursosdao.eliminarCurso(curso)
        }
    }
}