package pe.edu.ulima.pm.ulgamestore.network

import pe.edu.ulima.pm.ulgamestore.model.Videogame
import retrofit2.Call
import retrofit2.http.GET

// https://script.google.com/macros/s/AKfycbxA2iW6e4f8IMlrIHIG_s1aRYDZDkhuKX1oKFARFFGe1du3fDM

interface APIVideogamesService {
    @GET("exec")
    fun getAllVideogames() : Call<List<Videogame>>
}