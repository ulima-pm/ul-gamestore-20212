package pe.edu.ulima.pm.ulgamestore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.ulgamestore.R
import pe.edu.ulima.pm.ulgamestore.model.Videogame

class ProductsListAdapter(
        private val productsList : List<Videogame>,
        private val fragment : Fragment,
        private val listener : (Videogame) -> Unit) :
    RecyclerView.Adapter<ProductsListAdapter.ViewHolder>(){

        class ViewHolder(
                view : View, val listener : (Videogame) -> Unit,
                val productsList : List<Videogame>) : RecyclerView.ViewHolder(view), View.OnClickListener {

            val iviProductImage : ImageView
            val tviProductName : TextView
            val tviProductPrice : TextView

            init {
                iviProductImage = view.findViewById(R.id.iviProductImage)
                tviProductName = view.findViewById(R.id.tviProductName)
                tviProductPrice = view.findViewById(R.id.tviProductPrice)
                view.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                listener(productsList[adapterPosition])
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        val viewHolder = ViewHolder(view, listener, productsList)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tviProductName.text = productsList[position].name
        holder.tviProductPrice.text = productsList[position].price.toString()
        Glide.with(fragment)
            .load(productsList[position].url)
            .fitCenter()
            .into(holder.iviProductImage)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}