package com.softurasolutions.tinbitoo.ui.promociones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.softurasolutions.tinbitoo.Modelos.FiltrosModelo
import com.softurasolutions.tinbitoo.R
import com.softurasolutions.tinbitoo.RequestMethods.ClassConectionPOST
import com.softurasolutions.tinbitoo.SettingsConst
import com.softurasolutions.tinbitoo.databinding.FragmentPromocionesBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.ExecutionException

class fragment_promociones : Fragment() {
    private var _binding: FragmentPromocionesBinding?=null
    private val binding get() = _binding!!

    var settingsConst = SettingsConst()

    private var items : ArrayList<ModeloPromos>?= null
    private var alphaAdapters: AdapterPromos?= null
    private var recyclerView: RecyclerView?= null
    private var loaderPromos: RelativeLayout?=null
    //LOADER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromocionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerViewPromos
        loaderPromos = binding.loaderPromos

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.layoutManager = layoutManager

        val girosList = ArrayList<String>()
        girosList.add("Giro")

        val promosList : ArrayList<ModeloPromos> = ArrayList()
        val empleado = FiltrosModelo("",0,"",10, 19.289684F,-98.23944F,null,true,2,null,32,20,null,
            0,null,"SI",null,true,null,"",1,"R")

        val gson = Gson()

        val representacionJSON = gson.toJson(empleado)
        //Imprimir para verifica la salida del JSON

        val objetoJson = JSONObject(representacionJSON)

        val servicio = ClassConectionPOST(
            settingsConst.servicio+"/api/promociones/buscar/publicadas",objetoJson
        )
        try {
            val thread = Thread {
                loaderPromos!!.visibility = View.VISIBLE
                if (settingsConst.hayConexion(requireContext())){
                    val response = servicio.execute().get()

                    val jsonObject = response!!.response.let {JSONObject(it)}
                    val code: Int = jsonObject.getString("code").toInt()

                    val message = jsonObject.getString("message")

                    if(code==200){
                        val data = jsonObject.getString("data")
                        val jsonArr = JSONArray(data)

                        for(j in 0 until jsonArr.length()){
                            val objectInArray = jsonArr.getJSONObject(j)
                            //Imprmir object in array
                            val giro = objectInArray.getString("giro_negocio")
                            val categoria_negocio = objectInArray.getString("categoria_negocio")
                            var url_imagen = objectInArray.getString("url_imagen")
                            val tipo_promocion = objectInArray.getString("tipo_promocion")
                            val descripcion = objectInArray.getString("descripcion")
                            val fecha_alta = objectInArray.getString("fecha_alta")
                            val nombre_comercial = objectInArray.getString("nombre_comercial")
                            val terminos = objectInArray.getString("terminos")
                            val abierto = objectInArray.getString("abierto")
                            val url_imagen_banner = objectInArray.getString("url_imagen_banner")

                            if(url_imagen.equals("")){
                                url_imagen = if(url_imagen_banner != "") url_imagen_banner else "https://ecoevents.blob.core.windows.net/comprandoando/BitooLogo.png"
                            }
                            if(girosList.contains(giro)){
                                promosList.add(ModeloPromos(categoria_negocio,url_imagen,giro,false,tipo_promocion,descripcion,fecha_alta,nombre_comercial,terminos,abierto))
                            }else{
                                girosList.add(giro)
                                promosList.add(ModeloPromos(categoria_negocio,url_imagen,giro,true,tipo_promocion,descripcion,fecha_alta,nombre_comercial,terminos,abierto))
                            }
                        }
                        if(isAdded){
                            requireActivity().runOnUiThread{
                                items = ArrayList()
                                items = promosList
                                alphaAdapters = AdapterPromos(requireContext(), items!!)
                                recyclerView?.adapter = alphaAdapters
                                loaderPromos!!.visibility = View.GONE
                            }
                        }
                    }else{
                        requireActivity().runOnUiThread{
                            loaderPromos!!.visibility = View.GONE
                            Toast.makeText(requireContext(), message[0].toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    requireActivity().runOnUiThread{
                        loaderPromos!!.visibility = View.GONE
                        Toast.makeText(requireContext(), "No hay conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            thread.start()

        }catch (e: ExecutionException){
            e.printStackTrace()
        }catch (e: JSONException){
            e.printStackTrace()
        }catch (e: InterruptedException){
            e.printStackTrace()
        }



























    }


}