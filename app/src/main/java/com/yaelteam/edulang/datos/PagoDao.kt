package com.yaelteam.edulang.datos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PagoDao {


    @Insert
    suspend fun insertarPago(pago: Pago)


    @Query("SELECT * FROM pagos ORDER BY fecha DESC")
    fun obtenerTodosLosPagos(): Flow<List<Pago>>


    @Query("SELECT * FROM pagos WHERE tipo_pago LIKE '%' || :texto || '%' ORDER BY fecha DESC")
    fun buscarPago(texto: String): Flow<List<Pago>>


    @Update
    suspend fun actualizarPago(pago: Pago)


    @Delete
    suspend fun eliminarPago(pago: Pago)
}