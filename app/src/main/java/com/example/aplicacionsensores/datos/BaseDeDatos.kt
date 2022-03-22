package com.example.aplicacionsensores.datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionsensores.datos.daos.AcelerometroDao
import com.example.aplicacionsensores.datos.entidades.Acelerometro

@Database(entities = [Acelerometro::class], version = 1)
abstract class BaseDeDatos : RoomDatabase() {
    abstract fun acelerometroDao() : AcelerometroDao

    companion object {
        @Volatile
        private var INSTANCE : BaseDeDatos? = null

        fun getDatabase(context: Context) : BaseDeDatos {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "acc_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}