package pe.edu.ulima.pm.ulgamestore.model

data class Videogame(
    val id : Long,
    val nombre : String,
    val categoria : Int,
    val consolas : String,
    val desarrollador : String,
    val ranking : Float,
    val precio : Float,
    val url : String
)