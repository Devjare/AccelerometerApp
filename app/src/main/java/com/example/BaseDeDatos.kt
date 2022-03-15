package com.example

import androidx.room.Database
import androidx.room.RoomDatabase
import daos.AcelerometroDao
import entidades.Acelerometro

@Database(entities = [Acelerometro::class], version = 1)
abstract class BaseDeDatos : RoomDatabase() {
    abstract fun acelerometroDao() : AcelerometroDao
}