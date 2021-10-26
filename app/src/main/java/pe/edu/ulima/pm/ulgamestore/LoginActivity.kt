package pe.edu.ulima.pm.ulgamestore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class LoginActivity : AppCompatActivity(){
    private lateinit var eteUsername : EditText
    private lateinit var etePassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isLogued()) {
            val sp = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE)
            val username = sp.getString("LOGIN_USERNAME", "")
            changeActivity(username!!)
        }

        eteUsername = findViewById(R.id.eteUsername)
        etePassword = findViewById(R.id.etePassword)

        val butLogin : Button = findViewById(R.id.butLogin)
        butLogin.setOnClickListener{ _ : View ->
            if (eteUsername.text.toString() == "pm" && etePassword.text.toString() == "123") {

                almacenarInfoLogin(eteUsername.text.toString())
                changeActivity(eteUsername.text.toString())

            }else {
                Toast.makeText(this, "Error en login", Toast.LENGTH_LONG).show()
            }
        }

        val butSignup : Button = findViewById(R.id.butSignup)
        butSignup.setOnClickListener { _ : View ->
            val intent : Intent = Intent(this, SignupActivity::class.java)
            startActivityForResult(intent, 10)
        }
    }

    private fun isLogued(): Boolean {
        val sp = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE)
        val username = sp.getString("LOGIN_USERNAME", "")!!
        if (username == "") return false

        val date = sp.getLong("LOGIN_DATE", 0)
        val currentDate = Date().time
        // Revisando que haya pasado mas de 5 minutos desde el login anterior
        if ( currentDate > date + 300_000) {
            return false

        }
        return true;

    }

    private fun almacenarInfoLogin(username : String) {
        val editor = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE).edit()
        editor.putString("LOGIN_USERNAME", username)
        editor.putLong("LOGIN_DATE", Date().time)
        editor.commit()
    }

    private fun changeActivity(username : String) {
        // Pasar al activity main
        val intent : Intent = Intent()
        intent.setClass(this, MainActivity::class.java)

        val bundle : Bundle = Bundle()
        bundle.putString("username", username)

        intent.putExtra("data",bundle)

        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10) {
            // Caso signup
            if (resultCode == RESULT_OK) {
                // Registro exitoso
                val username = data?.getBundleExtra("signup_data")?.getString("username")
                val password = data?.getBundleExtra("signup_data")?.getString("password")

                eteUsername.setText(username)
                etePassword.setText(password)
            } else {
                eteUsername.setText("")
                etePassword.setText("")

            }
        }
    }
}