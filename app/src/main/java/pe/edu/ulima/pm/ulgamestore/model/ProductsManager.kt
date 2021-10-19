package pe.edu.ulima.pm.ulgamestore.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pe.edu.ulima.pm.ulgamestore.network.NetworkClient
import java.util.logging.Handler

class ProductsManager {
    fun getProducts(callbackOK : (List<Videogame>) -> Unit, callbackError : (String) -> Unit) {

        val networkClient = NetworkClient("")

        /*
        * [
        *    {"id" : "1", "name", "Fortnite", "price" : 20.5, "url": "sdfsdfs"},
        *    {"id" : "2", "name", "asdsada", "price" : 20.5, "url": "sdfsdfs"},
        *    {}
        * ]
        *
         */

        val handler = HandlerCompat.createAsync(Looper.myLooper()!!) // main thread
        networkClient.download({data : String ->
            // ok
            val collectionType = (object : TypeToken<List<Videogame>>() {}).type
            val gson = Gson()
            val listVideogames = gson.fromJson<List<Videogame>>(data, collectionType)
            handler.post { callbackOK(listVideogames) } // se ejecuta en main thread

        }, { error : String ->
            // error
            handler.post { callbackError(error) } // se ejecuta en main thread
        })

//        val videogames = arrayListOf<Videogame>()
//        videogames.add(Videogame(1, "Fortnite", 20.5f, "https://www.tonica.la/__export/1587577404181/sites/debate/img/2020/04/22/fortnite-portada_1.jpg_463833556.jpg"))
//        videogames.add(Videogame(2, "Fifa 22", 40f, "https://esports.eldesmarque.com/wp-content/uploads/2021/08/FIFA-22-681x382.jpg"))
//        return videogames
    }
}