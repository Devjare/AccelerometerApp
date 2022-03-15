package entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Acelerometro(
    @ColumnInfo(name = "x") var x: Float?,
    @ColumnInfo(name = "y") var y: Float?,
    @ColumnInfo(name = "z") var z: Float?,
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = 1, // Autoincrementable primary key.
)