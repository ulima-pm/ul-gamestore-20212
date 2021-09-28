package pe.edu.ulima.pm.ulgamestore.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.ulgamestore.MainActivity
import pe.edu.ulima.pm.ulgamestore.R

class BottomBarFragment : Fragment() {
    interface OnMenuClicked {
        fun onClick(menuName : String)
    }

    private var listener : OnMenuClicked? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? OnMenuClicked
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottombar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val butMenuProducts = view.findViewById<Button>(R.id.butMenuProducts)
        val butMenuAccount = view.findViewById<Button>(R.id.butMenuAccount)

        butMenuProducts.setOnClickListener { _ : View ->
            // Cambiar al fragment ProductsFragment
            //(activity as MainActivity).changeProductsFragment()
            listener?.onClick("products")
        }
        butMenuAccount.setOnClickListener { _ : View ->
            // Cambiar al fragment AccountFragment
            //(activity as MainActivity).changeAccountFragment()
            listener?.onClick("account")
        }
    }

    fun setOnClickListener(listener : OnMenuClicked) {
        this.listener = listener
    }
}