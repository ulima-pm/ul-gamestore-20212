package pe.edu.ulima.pm.ulgamestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.ulgamestore.MainActivity
import pe.edu.ulima.pm.ulgamestore.R

class ProductsFragment(val activity : MainActivity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity.metodoCualquiera()
        return inflater.inflate(R.layout.fragment_products, container, false)
    }
}