package daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entidades.Acelerometro

@Dao
interface AcelerometroDao {
   @Query("SELECT * FROM acelerometro")
   fun getAll(): List<Acelerometro>

   @Insert
   fun insertAll(magnitudes: List<Acelerometro>)

   @Delete
   fun delete(acc: Acelerometro)
}