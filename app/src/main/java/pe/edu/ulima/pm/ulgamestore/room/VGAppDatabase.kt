package pe.edu.ulima.pm.ulgamestore.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.ulima.pm.ulgamestore.model.Videogame

@Database(entities = [Videogame::class], version = 1)
abstract class VGAppDatabase : RoomDatabase(){
    abstract fun videogameDAO() : VideogameDAO
}