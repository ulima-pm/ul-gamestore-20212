package pe.edu.ulima.pm.ulgamestore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pe.edu.ulima.pm.ulgamestore.model.LoginManager

class SignupActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        findViewById<Button>(R.id.butSignup).setOnClickListener { v : View ->
            // Guardar los datos en algun servicio de persistencia

            LoginManager.instance.saveUser(
                findViewById<EditText>(R.id.eteName).text.toString(),
                findViewById<EditText>(R.id.eteUsername).text.toString(),
                findViewById<EditText>(R.id.etePassword).text.toString(),
                {
                    val intent : Intent = Intent()
                    val bundle = Bundle()
                    bundle.putString("username", findViewById<EditText>(R.id.eteUsername).text.toString())
                    bundle.putString("password", findViewById<EditText>(R.id.etePassword).text.toString())
                    intent.putExtra("signup_data", bundle)

                    setResult(RESULT_OK, intent)
                    finish()
                },
                {
                    Log.e("SignupActivity", it)
                    Toast.makeText(this, "Error guardando usuario", Toast.LENGTH_SHORT).show()
                }
            )


        }

        findViewById<Button>(R.id.butCancel).setOnClickListener { v: View ->
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}