package com.softurasolutions.tinbitoo.ui.productos

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.softurasolutions.tinbitoo.R
import com.squareup.picasso.Picasso

class AdapterProductos(var context: Context, var arrayList: ArrayList<ModeloProductos>) :
    RecyclerView.Adapter<AdapterProductos.ItemHolder>() {

    var modeloProductos: ArrayList<ModeloProductos> = arrayList

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagen_promo: ImageView = itemView.findViewById(R.id.img_producto)
        var url: TextView = itemView.findViewById(R.id.txt_url)
        var descripcion: TextView = itemView.findViewById(R.id.txt_descripcion)
        var delete: ImageView = itemView.findViewById(R.id.deleteProducto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_card_producto, parent, false)
        return AdapterProductos.ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val cardItem: ModeloProductos = arrayList[position]

        Picasso.get().load(cardItem.imagen!!).into(holder.imagen_promo)
        holder.url.text = cardItem.url
        holder.descripcion.text = cardItem.descripcion


        holder.delete.setOnClickListener{
            val idPOs = holder.adapterPosition
            /*
            *
            * Hacemos toda la logica, consulta de servicio API para eliminar el producto
            *
            * */
            modeloProductos.removeAt(idPOs)//De a cuerdo a la respsuesta del servicio eliminamos y actualizamos nuestra lista
            Log.e("Tamaño",":"+modeloProductos.size)
            this@AdapterProductos.notifyItemRemoved(idPOs)
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }
    }
}