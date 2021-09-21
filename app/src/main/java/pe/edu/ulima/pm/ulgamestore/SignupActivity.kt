package pe.edu.ulima.pm.ulgamestore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        findViewById<Button>(R.id.butSignup).setOnClickListener { v : View ->
            // Guardar los datos en algun servicio de persistencia

            val intent : Intent = Intent()
            val bundle = Bundle()
            bundle.putString("username", findViewById<EditText>(R.id.eteUsername).text.toString())
            bundle.putString("password", findViewById<EditText>(R.id.etePassword).text.toString())
            intent.putExtra("signup_data", bundle)

            setResult(RESULT_OK, intent)
            finish()
        }

        findViewById<Button>(R.id.butCancel).setOnClickListener { v: View ->
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}