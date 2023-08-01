package com.softurasolutions.tinbitoo.ui.productos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.softurasolutions.tinbitoo.R
import com.softurasolutions.tinbitoo.databinding.FragmentProductosBinding
import java.io.UnsupportedEncodingException
import java.util.Objects
import java.util.concurrent.TimeUnit

class fragment_productos : Fragment() {

    private val CODIGO_PERMISO_CAMARA = 2108
    private var _binding : FragmentProductosBinding?=null
    private val binding get() = _binding

    private var barcodeView : DecoratedBarcodeView?= null
    private var beepManager : BeepManager?=null
    private var  switchFlashButton: FloatingActionButton ?= null
    private var qrScannerView : DecoratedBarcodeView?= null
    private var  toggleScannerView: CardView?= null
    private var recyclerViewProductos: RecyclerView?=null
    private var flash: Boolean = false
    private var scannerVisibe : Boolean = true
    private var productosListy: ArrayList<ModeloProductos> = ArrayList()
    private var items :ArrayList<ModeloProductos>?= null
    private var itemsAdapters: AdapterProductos? = null

    private val callback: BarcodeCallback = BarcodeCallback { result ->
        barcodeView?.resume()
        vibratePhone()
        if (result!!.text == null){
            Toast.makeText(requireContext(), "No se ha escaneado nada", Toast.LENGTH_SHORT).show()
            try {
                TimeUnit.SECONDS.sleep(1)
            }catch (e: InterruptedException){
                e.printStackTrace()
            }
        } else if(result.text != null){
            barcodeView!!.setStatusText(result.text)
            beepManager!!.playBeepSound()
            TimeUnit.SECONDS.sleep(1)
            try {
                Log.e("CODE","---> ${result.text}")
                productosListy.add(ModeloProductos(result.text,"https://es.qr-code-generator.com/wp-content/themes/qr/new_structure/assets/media/images/exit_intent/hand-holding-free-qr-codes.png",result.text))

                if(isAdded){
                    requireActivity().runOnUiThread{
                        items = ArrayList()
                        items = productosListy
                        itemsAdapters = AdapterProductos(requireContext(), items!!)
                        recyclerViewProductos?.adapter = itemsAdapters
                    }
                }

            }catch (e: UnsupportedEncodingException){
                e.printStackTrace()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        return  binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()

        barcodeView = binding!!.zxingBarcodeScanner
        qrScannerView = binding!!.zxingBarcodeScanner
        qrScannerView!!.setTorchOff()
        switchFlashButton = binding!!.floatingTurnFlash
        recyclerViewProductos = binding!!.recyclerViewProductos
        toggleScannerView = binding!!.toggleScanner
        val toggleContainer = binding!!.toggleCXontainer
        val layoutParams = toggleContainer.layoutParams as ViewGroup.MarginLayoutParams
        val arrow : ImageView = binding!!.arrow

        val formats: Collection<BarcodeFormat> =
        listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)

        barcodeView!!.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView!!.initializeFromIntent(requireActivity().intent)
        barcodeView!!.decodeContinuous(callback)
        barcodeView!!.setStatusText("Enfoque un código")
        beepManager = BeepManager(requireActivity())

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerViewProductos?.layoutManager = layoutManager

        switchFlashButton!!.setOnClickListener {
            flash = if(!flash){
                barcodeView!!.setTorchOn()
                true
            }else{
                barcodeView!!.setTorchOff()
                false
            }
        }

        toggleScannerView!!.setOnClickListener {
            scannerVisibe = if (scannerVisibe){
                barcodeView!!.pause()
                barcodeView!!.animate()
                    .alpha(0.0f)
                    .setDuration(300)
                    .withEndAction{
                        barcodeView!!.visibility = View.GONE
                        barcodeView!!.alpha = 1.0f
                    }
                layoutParams.setMargins(0, 10, 0, 10)
                arrow.setImageResource(R.drawable.arrow_circle_down)
                false
            }else{
                barcodeView!!.resume()
                barcodeView!!.animate()
                    .alpha(1.0f)
                    .setDuration(300)
                    .setListener(null)

                barcodeView!!.visibility = View.VISIBLE

                layoutParams.setMargins(0, -20, 0, 0)
                arrow.setImageResource(R.drawable.arrow_circle_up)
                true
            }
        }


    }

    private  fun checkPermission(){
        if(ContextCompat.checkSelfPermission(
                Objects.requireNonNull(
                    requireContext()
                ),
                Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        ){
         requestPermissions(arrayOf(Manifest.permission.CAMERA), CODIGO_PERMISO_CAMARA)
        }else if(ContextCompat.checkSelfPermission(
                Objects.requireNonNull(
                    requireContext()
                ),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        ){
            //PERMISOS HAN SIDO CONCEDIDOS
        }
    }

    fun vibratePhone(){
        val vibrador = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrador.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
    }


    override fun onDestroy() {
        super.onDestroy()
        barcodeView!!.destroyDrawingCache()
    }

    override fun onResume() {
        super.onResume()
        barcodeView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView!!.pause()
    }









}