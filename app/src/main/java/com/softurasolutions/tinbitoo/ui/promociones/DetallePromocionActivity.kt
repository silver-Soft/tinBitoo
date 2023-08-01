package com.softurasolutions.tinbitoo.ui.promociones

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.softurasolutions.tinbitoo.R
import com.softurasolutions.tinbitoo.databinding.ActivityDetallePromocionBinding

class DetallePromocionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePromocionBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePromocionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tv_negocio : TextView = binding.tvNegocio
        val tv_promo : TextView = binding.tvPromocion


        val descripcion = intent.getStringExtra("descripcion")
        val nombre_negocio = intent.getStringExtra("nombre_negocio")

        tv_negocio.text = "Vienes del negocio " +nombre_negocio
        tv_promo.text = "Con la promocion " +descripcion

    }
}