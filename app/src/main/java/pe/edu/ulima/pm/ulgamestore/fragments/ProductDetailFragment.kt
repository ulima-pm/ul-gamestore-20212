package pe.edu.ulima.pm.ulgamestore.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import pe.edu.ulima.pm.ulgamestore.R
import pe.edu.ulima.pm.ulgamestore.adapter.ProductsListAdapter
import pe.edu.ulima.pm.ulgamestore.model.ProductsManager

class ProductDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_productdetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager = view.findViewById<ViewPager2>(R.id.pager)
        pager.adapter = ProductsListAdapter(ProductsManager().getProducts(), this) {
            Log.i("ProductDetailFragment", "Se hizo click");
        }
    }
}