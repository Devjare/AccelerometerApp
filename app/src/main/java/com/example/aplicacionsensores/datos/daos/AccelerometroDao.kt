package com.example.aplicacionsensores.datos.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionsensores.datos.entidades.Acelerometro

@Dao
interface AcelerometroDao {
   @Query("SELECT * FROM acelerometro")
   fun getAll(): List<Acelerometro>

   @Query("SELECT * FROM acelerometro ORDER BY uid DESC LIMIT 1")
   fun getLast() : Acelerometro

   @Query("SELECT COUNT(*) FROM acelerometro")
   fun getCount() : Int

   @Insert
   fun insertAll(magnitudes: List<Acelerometro>)

   @Delete
   fun delete(acc: Acelerometro)
}