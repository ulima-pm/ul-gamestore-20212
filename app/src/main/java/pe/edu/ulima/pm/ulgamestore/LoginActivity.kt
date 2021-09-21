package pe.edu.ulima.pm.ulgamestore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    private lateinit var eteUsername : EditText
    private lateinit var etePassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        eteUsername = findViewById(R.id.eteUsername)
        etePassword = findViewById(R.id.etePassword)

        val butLogin : Button = findViewById(R.id.butLogin)
        butLogin.setOnClickListener{ _ : View ->
            if (eteUsername.text.toString() == "pm" && etePassword.text.toString() == "123") {
                // Pasar al activity main
                val intent : Intent = Intent()
                intent.setClass(this, MainActivity::class.java)

                val bundle : Bundle = Bundle()
                bundle.putString("username", eteUsername.text.toString())

                intent.putExtra("data",bundle)

                startActivity(intent)
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