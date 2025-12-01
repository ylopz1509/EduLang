package com.yaelteam.edulang.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaelteam.edulang.datos.BaseDeDatosEduLang
import com.yaelteam.edulang.datos.Pago
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PagosViewModel(context: Context) : ViewModel() {

    private val dao = BaseDeDatosEduLang.obtenerBaseDatos(context).pagoDao()


    var textoBusqueda by mutableStateOf("")


    val listaPagos: StateFlow<List<Pago>> =
        snapshotFlow { textoBusqueda }
            .flatMapLatest { consulta ->
                if (consulta.isBlank()) dao.obtenerTodosLosPagos()
                else dao.buscarPago(consulta)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    var idEstudiante by mutableStateOf("")
    var idCurso by mutableStateOf("")
    var tipoPago by mutableStateOf("")
    var monto by mutableStateOf("")
    var fecha by mutableStateOf("")


    fun guardarPago(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val pago = Pago(
                idEstudiante = idEstudiante.toInt(),
                idCurso = idCurso.toInt(),
                tipo_pago = tipoPago,
                monto = monto.toDouble(),
                fecha = fecha
            )
            dao.insertarPago(pago)

            // limpiar campos
            idEstudiante = ""
            idCurso = ""
            tipoPago = ""
            monto = ""
            fecha = ""

            onSuccess()
        }
    }


    fun eliminarPago(pago: Pago) {
        viewModelScope.launch {
            dao.eliminarPago(pago)
        }
    }
}