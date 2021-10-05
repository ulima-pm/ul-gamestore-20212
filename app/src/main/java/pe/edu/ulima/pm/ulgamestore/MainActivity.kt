package pe.edu.ulima.pm.ulgamestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import pe.edu.ulima.pm.ulgamestore.fragments.AccountFragment
import pe.edu.ulima.pm.ulgamestore.fragments.BottomBarFragment
import pe.edu.ulima.pm.ulgamestore.fragments.ProductsFragment

class MainActivity : AppCompatActivity(), BottomBarFragment.OnMenuClicked {

    private val fragments = mutableListOf<Fragment>()
    private lateinit var dlaMain : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(ProductsFragment())
        fragments.add(AccountFragment())

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

    override fun onClick(menuName: String) {
        if (menuName == "products") {
            changeProductsFragment()
        }else if (menuName == "account"){
            changeAccountFragment()
        }
    }
}