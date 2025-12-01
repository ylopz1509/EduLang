package com.yaelteam.edulang.datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// (Usuario y estudiante)
@Database(
    entities = [Usuario::class, Estudiante::class, Curso::class],
    version = 1,
    exportSchema = false
)
abstract class BaseDeDatosEduLang : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
    abstract fun cursoDao(): CursoDao
    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatosEduLang? = null

        fun obtenerBaseDatos(context: Context): BaseDeDatosEduLang {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatosEduLang::class.java,
                    "edulang_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}