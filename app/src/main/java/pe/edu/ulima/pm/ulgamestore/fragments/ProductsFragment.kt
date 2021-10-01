package pe.edu.ulima.pm.ulgamestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.ulgamestore.MainActivity
import pe.edu.ulima.pm.ulgamestore.R
import pe.edu.ulima.pm.ulgamestore.adapter.ProductsListAdapter
import pe.edu.ulima.pm.ulgamestore.model.ProductsManager

class ProductsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rviProducts = view.findViewById<RecyclerView>(R.id.rviProducts)
        rviProducts.adapter = ProductsListAdapter(ProductsManager().getProducts())

    }
}