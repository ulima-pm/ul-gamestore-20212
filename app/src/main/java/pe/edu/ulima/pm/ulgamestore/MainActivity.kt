package pe.edu.ulima.pm.ulgamestore

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import pe.edu.ulima.pm.ulgamestore.fragments.AccountFragment
import pe.edu.ulima.pm.ulgamestore.fragments.BottomBarFragment
import pe.edu.ulima.pm.ulgamestore.fragments.ProductDetailFragment
import pe.edu.ulima.pm.ulgamestore.fragments.ProductsFragment
import pe.edu.ulima.pm.ulgamestore.model.Videogame

class MainActivity : AppCompatActivity(), BottomBarFragment.OnMenuClicked,
        ProductsFragment.OnProductSelectedListener, SensorEventListener {

    private val fragments = mutableListOf<Fragment>()
    private lateinit var dlaMain : DrawerLayout
    private lateinit var sensorManager : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        fragments.add(ProductsFragment())
        fragments.add(AccountFragment())
        fragments.add(ProductDetailFragment())

        // Configurando menu hamburguesa
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_manage)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Configurando NavigationView
        val nviMain = findViewById<NavigationView>(R.id.nviMain)
        dlaMain = findViewById<DrawerLayout>(R.id.dlaMain)

        nviMain.setNavigationItemSelectedListener { menuItem : MenuItem ->
            if (menuItem.itemId == R.id.menProducts) {
                changeProductsFragment()
            }else if (menuItem.itemId == R.id.menAccount) {
                changeAccountFragment()
            }

            menuItem.isChecked = true
            dlaMain.closeDrawers()
            true
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaContent,fragments[0])

        ft.commit()

    }

    override fun onResume() {
        super.onResume()
        showTiltData()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun showTiltData() {
        val sensor = getSensor("android.sensor.tilt_detector")
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun getSensor(type : String) : Sensor? {
        val deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for ( sensor in deviceSensors) {
            if (sensor.stringType == type) return sensor
        }
        return null
    }

    private fun showAvailableSensors() {
        val deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        deviceSensors.forEach {
            Log.d("MainActivity", "${it.name} : ${it.stringType}")

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            dlaMain.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun changeProductsFragment() {
        val fragment = fragments[0]
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent,fragment)

        ft.commit()
    }

    private fun changeAccountFragment() {
        val fragment = fragments[1]
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)

        ft.commit()
    }

    private fun changeProductDetailFragment(product : Videogame) {
        val fragment = fragments[2]
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)

        ft.commit()
    }

    override fun onClick(menuName: String) {
        if (menuName == "products") {
            changeProductsFragment()
        }else if (menuName == "account"){
            changeAccountFragment()
        }
    }

    override fun onSelect(videogame: Videogame) {
        changeProductDetailFragment(videogame)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        Log.d("MainActivity", sensorEvent!!.values[0].toString())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}