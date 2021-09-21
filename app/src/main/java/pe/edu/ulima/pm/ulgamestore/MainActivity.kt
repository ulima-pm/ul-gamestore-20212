package pe.edu.ulima.pm.ulgamestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val username = intent.getBundleExtra("data")?.getString("username")

        findViewById<TextView>(R.id.tviMensaje).text = username
    }
}