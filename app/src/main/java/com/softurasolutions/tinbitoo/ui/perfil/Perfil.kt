package com.softurasolutions.tinbitoo.ui.perfil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softurasolutions.tinbitoo.LoginActivity
import com.softurasolutions.tinbitoo.R
import com.softurasolutions.tinbitoo.databinding.FragmentPerfilBinding
import com.squareup.picasso.Picasso

class Perfil : Fragment() {
    private var _binding: FragmentPerfilBinding?=null
    private val binding get() = _binding!!

    private var cardItems : ArrayList<ModeloCardsPerfil>?= null
    private var cardAdapter: AdapterCardsPerfil ? =null
    private var recyclerView : RecyclerView ?= null
    private var gridLayoutManager : GridLayoutManager ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false )
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scrolview : ScrollView = binding.scrollPerfil
        val userPhoto : ImageView = binding.userPhoto
        val viewLogin : RelativeLayout = binding.ViewLogin
        val nombreUsuario : TextView = binding.tvNombreUsuario
        val userCorreo : TextView = binding.tvCorreoUsuario
        val btnLogin : Button = binding.btnLogin

        recyclerView = binding.cardsPerfil
        gridLayoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL,
            false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        val prefs = requireContext().getSharedPreferences("Preferences", 0)

        val token = prefs.getString("token","")
        val strUsuario = prefs.getString("nombreCompleto","")
        val correo = prefs.getString("correo","")
        val imgUser = prefs.getString("img_user","")

        val cardsPerfil: ArrayList<ModeloCardsPerfil> = ArrayList()

        if(token!=""){
            scrolview.visibility = View.VISIBLE
            viewLogin.visibility = View.GONE
            nombreUsuario.text = strUsuario
            userCorreo.text = correo
            Picasso.get().load(imgUser).into(userPhoto)

        }else{
            scrolview.visibility = View.GONE
            viewLogin.visibility = View.VISIBLE
        }

        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/512/553/553932.png","",false,"Mis negocios")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/8754/8754145.png","",false,"Mis anuncios y promociones")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/281/281743.png","",false,"Mis requerimientos de compra")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/10667/10667120.png","330",true,"Mis ventas")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/5465/5465858.png","",false,"Mis compras")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/9170/9170119.png","330",false,"Estadisticas")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/4208/4208408.png","330",false,"Mis preferencias")
        )
        cardsPerfil.add(
            ModeloCardsPerfil("https://cdn-icons-png.flaticon.com/128/2905/2905992.png","330",false,"Aviso de privacidad de cuenta")
        )

        cardItems = ArrayList()
        cardItems = cardsPerfil
        cardAdapter = AdapterCardsPerfil(requireContext(), cardItems!!)

        recyclerView?.adapter = cardAdapter



        btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            this.activity?.finish()
        }

    }

}