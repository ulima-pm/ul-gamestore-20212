package pe.edu.ulima.pm.ulgamestore.model

class ProductsManager {
    fun getProducts() : List<Videogame> {
        val videogames = arrayListOf<Videogame>()
        videogames.add(Videogame(1, "Fortnite", 20.5f, "https://www.tonica.la/__export/1587577404181/sites/debate/img/2020/04/22/fortnite-portada_1.jpg_463833556.jpg"))
        videogames.add(Videogame(2, "Fifa 22", 40f, "https://esports.eldesmarque.com/wp-content/uploads/2021/08/FIFA-22-681x382.jpg"))
        return videogames
    }
}