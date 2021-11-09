package pe.edu.ulima.pm.ulgamestore.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import pe.edu.ulima.pm.ulgamestore.MainActivity
import pe.edu.ulima.pm.ulgamestore.R
import pe.edu.ulima.pm.ulgamestore.adapter.ProductsListAdapter
import pe.edu.ulima.pm.ulgamestore.model.ProductsManager
import pe.edu.ulima.pm.ulgamestore.model.Videogame

class ProductsFragment : Fragment() {
    interface OnProductSelectedListener {
        fun onSelect(videogame : Videogame)
    }
    private var listener : OnProductSelectedListener? = null
    private lateinit var fusedLocationClient : FusedLocationProviderClient


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnProductSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        ProductsManager(requireActivity().applicationContext).getProductsFirebase({vgList : List<Videogame> ->
            val rviProducts = view.findViewById<RecyclerView>(R.id.rviProducts)
            rviProducts.adapter = ProductsListAdapter(
                vgList,
                this
            ) { product: Videogame ->
                listener?.onSelect(product)
            }
        }, { error ->
            Log.e("ProducsFragment", error)
            Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show()
        })

        getLastLocation()
    }

    private fun getLastLocation() {
        val permission = ContextCompat.checkSelfPermission(
            requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // No hay permisos
            val noPodemosPedirPermisos = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (noPodemosPedirPermisos) {
                Toast.makeText(requireActivity(),
                    "Habilitar permisos manualmente", Toast.LENGTH_SHORT).show()
            }else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION ,
                        android.Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
            }
        }else {
            // Ya tenemos permisos, podemos obtener localizacion
            fusedLocationClient.lastLocation.addOnSuccessListener {
                Log.d("ProductsFragment", "${it.latitude} , ${it.longitude}")
            }
            fusedLocationClient.lastLocation.addOnFailureListener {
                Log.e("ProductsFragment", it.message!!)
            }
        }
    }


}