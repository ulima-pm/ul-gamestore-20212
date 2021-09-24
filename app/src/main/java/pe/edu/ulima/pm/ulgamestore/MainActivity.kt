package pe.edu.ulima.pm.ulgamestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import pe.edu.ulima.pm.ulgamestore.fragments.ProductsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = ProductsFragment(this)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaContent,fragment)

        ft.commit()
    }

    fun metodoCualquiera() {

    }
}