package pe.edu.ulima.pm.ulgamestore.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import pe.edu.ulima.pm.ulgamestore.FotoActivity
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
    private lateinit var locationCallback: LocationCallback


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

        view.findViewById<Button>(R.id.butGoFotoActivity).setOnClickListener {
            val intent = Intent(requireActivity(), FotoActivity::class.java)
            startActivity(intent)
        }

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

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { habilitado ->
                if (habilitado) {
                    getLocation()
                }
            }

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
//                requestPermissions(
//                    arrayOf(
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION ,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    100
//                )

                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }else {
            getLocation()
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        getLocation()
//    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        // Obtener ultima localizacion
        fusedLocationClient.lastLocation.addOnSuccessListener {
            Log.d("ProductsFragment", "${it.latitude} , ${it.longitude}")
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            Log.e("ProductsFragment", it.message!!)
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallback = object  : LocationCallback() {
            override fun onLocationResult(locationResult : LocationResult?){
                if (locationResult != null) {
                    for (location in locationResult.locations) {
                        Log.d("ProductsFragment",
                            "${location.latitude} , ${location.longitude}")
                    }
                }
            }
        }

        // Configurar para obtener localizacion mas actualizadas
        fusedLocationClient.requestLocationUpdates(
            createLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun createLocationRequest() : LocationRequest {
        val locationRequest = LocationRequest.create().apply {
            this.interval = 5000
            this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        return locationRequest
    }

    fun stopLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

}