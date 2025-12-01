package com.yaelteam.edulang.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaelteam.edulang.datos.BaseDeDatosEduLang
import com.yaelteam.edulang.datos.Curso
import com.yaelteam.edulang.datos.Matricula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class HistorialEntry(
    val nombreEstudiante: String?,
    val matricula: String,
    val cursos: List<String>
)

class MatriculasViewModel(context: Context) : ViewModel() {

    private val db = BaseDeDatosEduLang.obtenerBaseDatos(context)
    private val estudianteDao = db.estudianteDao()
    private val cursoDao = db.cursoDao()
    private val matriculaDao = db.matriculaDao()

    // UI states
    var matriculaInput by mutableStateOf("")
    var cursoSeleccionadoTexto by mutableStateOf("") // texto mostrado
    var cursoSeleccionadoId: Int? by mutableStateOf(null)

    // Flows para listar cursos y matriculas
    val listaCursos: Flow<List<Curso>> = cursoDao.obtenerTodo()
    val listaMatriculasRaw: Flow<List<Matricula>> = matriculaDao.obtenerTodas()

    val historialAgrupado: Flow<List<HistorialEntry>> = combine(
        listaMatriculasRaw,
        estudianteDao.obtenerTodos(),
        listaCursos
    ) { matriculas, estudiantes, cursos ->
        val estudiantesPorMat = estudiantes.associateBy { it.matricula }
        val cursosPorId = cursos.associateBy { it.id }
        // Agrupar
        val map = linkedMapOf<String, MutableList<String>>()
        for (m in matriculas) {
            val lista = map.getOrPut(m.matriculaEstudiante) { mutableListOf() }
            val curso = cursosPorId[m.cursoId]
            val nombreCurso = if (curso != null) "${curso.idioma} ${curso.nivel}" else "Curso #${m.cursoId}"
            if (!lista.contains(nombreCurso)) lista.add(nombreCurso)
        }
        // Construir lista de HistorialEntry manteniendo orden de aparición de matriculas
        map.map { (mat, cursosList) ->
            val est = estudiantesPorMat[mat]
            HistorialEntry(
                nombreEstudiante = est?.nombreCompleto,
                matricula = mat,
                cursos = cursosList
            )
        }
    }

    fun inscribirCurso(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val matricula = matriculaInput.trim()
        val cursoId = cursoSeleccionadoId
        if (matricula.isEmpty()) {
            onError("Ingrese la matrícula del estudiante.")
            return
        }
        if (cursoId == null) {
            onError("Seleccione un curso válido.")
            return
        }

        viewModelScope.launch {
            // Validar que exista el estudiante
            val estudiante = estudianteDao.obtenerPorMatriculaDirecta(matricula)
            if (estudiante == null) {
                onError("No existe estudiante con matrícula: $matricula")
                return@launch
            }
            val curso = cursoDao.obtenerPorId(cursoId)
            if (curso == null) {
                onError("El curso seleccionado no existe.")
                return@launch
            }
            val nueva = Matricula(
                matriculaEstudiante = matricula,
                cursoId = cursoId
            )
            matriculaDao.insertarMatricula(nueva)
            // limpiar
            matriculaInput = ""
            cursoSeleccionadoId = null
            cursoSeleccionadoTexto = ""
            onSuccess()
        }
    }
}
