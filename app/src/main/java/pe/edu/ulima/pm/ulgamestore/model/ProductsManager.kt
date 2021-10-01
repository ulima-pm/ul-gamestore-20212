package pe.edu.ulima.pm.ulgamestore.model

class ProductsManager {
    fun getProducts() : List<Videogame> {
        val videogames = arrayListOf<Videogame>()
        videogames.add(Videogame(1, "Fortnite", 20.5f, ""))
        videogames.add(Videogame(2, "Fifa 22", 40f, ""))
        return videogames
    }
}