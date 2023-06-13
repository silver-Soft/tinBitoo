package com.softurasolutions.tinbitoo.ui.perfil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.softurasolutions.tinbitoo.R
import com.squareup.picasso.Picasso

class AdapterCardsPerfil(var context: Context, var arrayList: ArrayList<ModeloCardsPerfil>):
    RecyclerView.Adapter<AdapterCardsPerfil.ItemHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_card_perfil, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val cardItem: ModeloCardsPerfil = arrayList[position]

        Picasso.get().load(cardItem.icono).into(holder.icono)

        if(cardItem.showBadge == true){
            holder.badge.visibility = View.VISIBLE
            holder.badge.text = cardItem.badge
        }else{
            holder.badge.visibility = View.GONE
        }

        holder.descripcion.text = cardItem.descripcion

        holder.cardMenu.setOnClickListener{
            Toast.makeText(context, "Abrirmos activity de "+cardItem.descripcion,
                Toast.LENGTH_SHORT).show()
        }
    }


    class ItemHolder( itemView: View): RecyclerView.ViewHolder(itemView){
        var icono: ImageView = itemView.findViewById(R.id.icon_card_perfil)
        var badge: TextView = itemView.findViewById(R.id.badgeValor)
        var descripcion: TextView = itemView.findViewById(R.id.descrip_card_perfil)
        var cardMenu: RelativeLayout = itemView.findViewById(R.id.card_menu_perfil)
    }
}