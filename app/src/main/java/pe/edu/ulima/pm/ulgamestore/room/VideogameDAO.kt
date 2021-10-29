package pe.edu.ulima.pm.ulgamestore.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.pm.ulgamestore.model.Videogame

@Dao
interface VideogameDAO {
    @Query("SELECT * FROM Videogame")
    fun findAll() : List<Videogame>

    @Query("SELECT * FROM Videogame WHERE id=:id")
    fun findById(id : Long) : Videogame

    @Insert
    fun insert(videogame : Videogame)
}