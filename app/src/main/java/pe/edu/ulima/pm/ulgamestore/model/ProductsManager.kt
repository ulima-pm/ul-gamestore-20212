package pe.edu.ulima.pm.ulgamestore.model

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pe.edu.ulima.pm.ulgamestore.network.APIVideogamesService
import pe.edu.ulima.pm.ulgamestore.network.NetworkClient
import pe.edu.ulima.pm.ulgamestore.room.VGAppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Handler

class ProductsManager(context : Context) {

    private val db = Room.databaseBuilder(context,
            VGAppDatabase::class.java, "db_videogames").allowMainThreadQueries().build()

    val API_URL = "https://script.google.com/macros/s/AKfycbxA2iW6e4f8IMlrIHIG_s1aRYDZDkhuKX1oKFARFFGe1du3fDM/"

    fun getProducts(callbackOK : (List<Videogame>) -> Unit, callbackError : (String) -> Unit) {

        val networkClient = NetworkClient(API_URL)

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
    }

    fun getProductsRetrofit(callbackOK : (List<Videogame>) -> Unit, callbackError : (String) -> Unit) {
        // Creamos el cliente retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIVideogamesService::class.java)

        service.getAllVideogames().enqueue(object : Callback<List<Videogame>> {
            override fun onResponse(
                call: Call<List<Videogame>>,
                response: Response<List<Videogame>>
            ) {
                // Guardamos en bd local
                saveIntoRoom(response.body()!!)
                callbackOK(response.body()!!)
            }

            override fun onFailure(call: Call<List<Videogame>>, t: Throwable) {
                Log.e("ProductsManager", t.message!!)
                callbackError(t.message!!)
            }
        })

        /*val handler = HandlerCompat.createAsync(Looper.myLooper()!!) // main thread
        Thread(){
            val vgList = service.getAllVideogames().execute().body() // se realiza la comunicacion con el servidor
            handler.post{callbackOK(vgList!!)}
        }.start()*/

    }

    fun getProductsByRoom(callbackOK : (List<Videogame>) -> Unit, callbackError : (String) -> Unit) {
        val videogames : List<Videogame> = db.videogameDAO().findAll()
        callbackOK(videogames)
    }

    private fun saveIntoRoom(videogames: List<Videogame>) {
        videogames.forEach {
            db.videogameDAO().insert(it)
        }
    }
}