package com.softurasolutions.tinbitoo.ui.promociones

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softurasolutions.tinbitoo.R
import com.squareup.picasso.Picasso
import java.lang.IllegalArgumentException

class AdapterPromos (var context: Context, var arrayList: ArrayList<ModeloPromos>) :
 RecyclerView.Adapter<AdapterPromos.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_card_promo, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val cardItem: ModeloPromos = arrayList[position]

        holder.categoria.text=cardItem.categoria

        if(cardItem.indexado == true){
            holder.categoria.visibility= View.VISIBLE
        }else{
            holder.categoria.visibility= View.GONE
        }

        try {
            Picasso.get().load(cardItem.imagen!!).into(holder.imagen)
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }

        holder.giro.text= cardItem.giro
        holder.tipoPromo.text= cardItem.tipoPromo
        holder.descripPromo.text= cardItem.descripcion
        holder.fecha.text = cardItem.fecha
        holder.nombeNegocio.text = cardItem.nombre_negocio
        holder.terminos.text = cardItem.terminos
        holder.abiertoCerrado.text= cardItem.abierto

        val alterColor = R.color.red

        val nextColor = if(cardItem.abierto == "CERRADO") alterColor else R.color.green

        holder.abiertoCerrado.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                nextColor
            )
        )

        holder.cardPromo.setOnClickListener{
            val intent = Intent(context, DetallePromocionActivity::class.java)
            intent.putExtra("giro",cardItem.giro)
            intent.putExtra("tipoPromo",cardItem.tipoPromo)
            intent.putExtra("descripcion",cardItem.descripcion)
            intent.putExtra("fecha",cardItem.fecha)
            intent.putExtra("nombre_negocio",cardItem.nombre_negocio)
            intent.putExtra("abierto",cardItem.abierto)

            context.startActivity(intent)
        }


    }


    class ItemHolder ( itemView: View): RecyclerView.ViewHolder(itemView){
        val cardPromo : RelativeLayout = itemView.findViewById(R.id.cardPromo)
        val categoria : TextView = itemView.findViewById(R.id.text_Categoria)
        var imagen: ImageView = itemView.findViewById(R.id.img_promo)
        var giro: TextView = itemView.findViewById(R.id.giroPromo)
        var tipoPromo: TextView = itemView.findViewById(R.id.txt_tipoPromo)
        var descripPromo: TextView = itemView.findViewById(R.id.txt_descripcion)
        var fecha: TextView = itemView.findViewById(R.id.txt_fecha)
        var nombeNegocio: TextView = itemView.findViewById(R.id.nombre_negocioPromo)
        var terminos: TextView = itemView.findViewById(R.id.txt_terminos)
        var abiertoCerrado: TextView = itemView.findViewById(R.id.txt_abierto_cerrado)
    }
}