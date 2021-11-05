package pe.edu.ulima.pm.ulgamestore.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// Singleton
class LoginManager {
    companion object {
        var instance : LoginManager = LoginManager()
            private set
    }

    private val dbFirebase = Firebase.firestore

    fun saveUser(nombre: String,
                 username : String,
                 password : String,
                 callbackOK : (Long) -> Unit,
                 callbackError: (String) -> Unit) {

        val data = hashMapOf<String, Any>(
            "nombre" to nombre,
            "username" to username,
            "password" to password
        )
        val userId = System.currentTimeMillis()
        dbFirebase.collection("usuarios").document(
            userId.toString()
        )
            .set(data)
            .addOnSuccessListener {
                callbackOK(userId)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}