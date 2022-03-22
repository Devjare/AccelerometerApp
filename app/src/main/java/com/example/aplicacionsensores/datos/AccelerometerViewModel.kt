package com.example.aplicacionsensores.datos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.aplicacionsensores.datos.daos.AcelerometroDao
import com.example.aplicacionsensores.datos.entidades.Acelerometro

class AccelerometerViewModel(application: Application) : AndroidViewModel(application) {
    private val accDao : AcelerometroDao

    init {
        accDao = BaseDeDatos.getDatabase(application).acelerometroDao()
    }

    fun addAll(values: List<Acelerometro>) {
        accDao.insertAll(values)
    }
}