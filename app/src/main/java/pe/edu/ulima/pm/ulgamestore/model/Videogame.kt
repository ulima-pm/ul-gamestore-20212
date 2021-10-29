package pe.edu.ulima.pm.ulgamestore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Videogame(
    @PrimaryKey
    val id : Long,
    @ColumnInfo(name = "nombre")
    val nombre : String,
    @ColumnInfo(name = "categoria")
    val categoria : Int,
    @ColumnInfo(name = "consolas")
    val consolas : String,
    @ColumnInfo(name = "desarrollador")
    val desarrollador : String,
    @ColumnInfo(name = "ranking")
    val ranking : Float,
    @ColumnInfo(name = "precio")
    val precio : Float,
    @ColumnInfo(name = "url")
    val url : String
)