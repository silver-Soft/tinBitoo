package com.softurasolutions.tinbitoo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softurasolutions.tinbitoo.RequestMethods.ClassConectionPOST
import com.softurasolutions.tinbitoo.SettingsConst
import com.softurasolutions.tinbitoo.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.NullPointerException
import java.util.concurrent.ExecutionException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var recyclerview : RecyclerView ?= null
    private var charItem: ArrayList<ModeloCard>?=null
    private var gridLayoutManager : GridLayoutManager ?= null
    private var aplhaAdapter :  Adapter?=null
    private var loaderHome: RelativeLayout?=null
    private var settingsConst = SettingsConst()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = binding.recyclerViewItem
        gridLayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        recyclerview?.layoutManager = gridLayoutManager

        recyclerview?.setHasFixedSize(true)
        loaderHome = binding.loaderHome
        val negocios : ArrayList<ModeloCard> = ArrayList()

        val cadena = "{\"filtros\":{\"organizacion\":null,\"id_persona\":null}}"

        val objetoJson = JSONObject(cadena)

        val servicioTask = ClassConectionPOST(
            settingsConst.servicio+"/api/negocios/obtenerPrincipalInicio", objetoJson
        )
        try {
            val thread = Thread{
                loaderHome!!.visibility = View.VISIBLE
                if(settingsConst.hayConexion(requireContext())){

                    val response = servicioTask.execute().get()
                    var message = JSONArray()
                    if(response!!.status==200){
                        val jsonObject = JSONObject(response.response)
                        message = JSONTokener(jsonObject.getString("message")).nextValue() as JSONArray

                        val data = jsonObject.getString("data")

                        val jsonArray = JSONArray(data)

                        for (j in 0 until jsonArray.length()){
                            val objectInArray = jsonArray.getJSONObject(j)

                            val arrNegocios = JSONArray(objectInArray.getString("negocios"))

                            for (i in 0 until arrNegocios.length()){
                                val urlLogo = arrNegocios.getJSONObject(i).getString("url_logo")
                                val giro = arrNegocios.getJSONObject(i).getString("giro")
                                val abierto = arrNegocios.getJSONObject(i).getString("abierto")
                                val likes = arrNegocios.getJSONObject(i).getString("likes")
                                val likeUsuario = arrNegocios.getJSONObject(i).getString("usuario_like")
                                val nombre = arrNegocios.getJSONObject(i).getString("nombre_comercial")

                                negocios.add(
                                    ModeloCard(
                                        urlLogo,
                                        giro,
                                        abierto,
                                        likes.toInt(),
                                        likeUsuario.toInt(),
                                        nombre
                                    )
                                )

                            }
                        }

                        try {
                            requireActivity().runOnUiThread(){
                                charItem = ArrayList()
                                charItem = negocios
                                aplhaAdapter = Adapter(requireContext(), charItem!!)
                                recyclerview?.adapter = aplhaAdapter
                                loaderHome!!.visibility = View.GONE
                            }
                        }catch (e: JSONException){
                            e.printStackTrace()
                            loaderHome!!.visibility = View.GONE
                        }
                    }else{
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), message[0].toString(), Toast.LENGTH_SHORT).show()
                            loaderHome!!.visibility = View.GONE
                        }
                    }

                }else{
                    requireActivity().runOnUiThread {
                        loaderHome!!.visibility = View.GONE
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}