package com.softurasolutions.tinbitoo.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.softurasolutions.tinbitoo.MainActivity
import com.softurasolutions.tinbitoo.R
import com.squareup.picasso.Picasso

class Adapter ( var context: Context, var arraylist: ArrayList<ModeloCard>) :
    RecyclerView.Adapter<Adapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_card_inicio, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val cardItem : ModeloCard = arraylist[position]

        Picasso.get().load(cardItem.imagen!!).into(holder.imagen)

        holder.giro.text = cardItem.giro
        holder.abierto.text = cardItem.abierto
        holder.likes.text = cardItem.likes.toString()
        holder.nombre.text= cardItem.nombre

        val altColor = R.color.red

        val nextColor = if (cardItem.abierto == "CERRADO") altColor else R.color.green

        holder.abierto.backgroundTintList = ColorStateList.valueOf(getColor(context, nextColor))

        if (cardItem.likeUsuario==1){
            holder.like_btn.setImageResource(R.drawable.ic_fav)
        }else{
            holder.like_btn.setImageResource(R.drawable.ic_fav_outline)
        }

        holder.like_btn.setOnClickListener {
            if(cardItem.likeUsuario==1){
                cardItem.likeUsuario = 0
                cardItem.likes = cardItem.likes?.minus(1)
                holder.likes.text = cardItem.likes.toString()
                holder.like_btn.setImageResource(R.drawable.ic_fav_outline)
                //aqui va la logica de dar like o quitar like
                //consumir servicio, consultar base de datos
                //dependiendo la respuesta conservar el estado
            }else{
                cardItem.likeUsuario = 1
                cardItem.likes = cardItem.likes?.plus(1)
                holder.likes.text = cardItem.likes.toString()
                holder.like_btn.setImageResource(R.drawable.ic_fav)
                //consumir un api

            }
        }

        holder.card.setOnClickListener {
            Toast.makeText(context, "Ha hecho click en "+cardItem.nombre, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return  arraylist.size
    }

    class ItemHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var imagen : ImageView = itemView.findViewById(R.id.icon_image_view)
        var giro : TextView = itemView.findViewById(R.id.giro)
        var abierto : TextView = itemView.findViewById(R.id.abiertocerrado)
        var likes : TextView = itemView.findViewById(R.id.likes)
        var like_btn : ImageView = itemView.findViewById(R.id.like_btn)
        var nombre : TextView = itemView.findViewById(R.id.nombre_negocio)
        var card : RelativeLayout = itemView.findViewById(R.id.background)

    }

}