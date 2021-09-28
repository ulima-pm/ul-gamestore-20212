package pe.edu.ulima.pm.ulgamestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.ulgamestore.fragments.AccountFragment
import pe.edu.ulima.pm.ulgamestore.fragments.BottomBarFragment
import pe.edu.ulima.pm.ulgamestore.fragments.ProductsFragment

class MainActivity : AppCompatActivity(), BottomBarFragment.OnMenuClicked {

    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(ProductsFragment())
        fragments.add(AccountFragment())

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaContent,fragments[0])

        ft.commit()
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