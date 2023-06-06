package com.softurasolutions.tinbitoo

import android.content.Context
import android.net.ConnectivityManager

class SettingsConst {

    var servicio = "http://132.145.168.176:8081"


    fun hayConexion(contexto: Context): Boolean{
        val isconnected : Boolean

        val cm = contexto.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNerwork = cm.activeNetworkInfo
        isconnected = activeNerwork != null && activeNerwork.isConnectedOrConnecting

        return isconnected
    }

}