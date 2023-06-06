package com.softurasolutions.tinbitoo.RequestMethods

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.nio.charset.StandardCharsets

class ClassConectionPOST (var linkrequestAPI: String, var posParams: JSONObject) : AsyncTask<Void?, Void?, ClassConectionPOST.MiObjeto?>(){
var code = 0

    private fun setCode(codeParam: Int): Int{
        this.code = codeParam
        return  codeParam

    }

    object MiObjeto{
        var status: Int=0
        var response : String = ""
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): MiObjeto {
        val wsURL = linkrequestAPI
        val url: URL

        try {
            url = URL(wsURL)
            val urlConection = url.openConnection() as HttpURLConnection

            val postDataParams: JSONObject = posParams

            urlConection.readTimeout = 15000
            urlConection.connectTimeout = 15000
            urlConection.requestMethod = "POST"
            urlConection.setRequestProperty("Content-Type", "application/json")
            urlConection.doInput = true
            urlConection.doOutput = true

            // OBTENIENDO RESPONSE

            val os = urlConection.outputStream

            val writter = BufferedWriter(OutputStreamWriter(os, StandardCharsets.UTF_8))
            writter.write(postDataParams.toString())
            writter.flush()
            writter.close()
            os.close()

            val responseCode = urlConection.responseCode//<-- Obtenemos nuestro codigo de respuesta

            setCode(responseCode)

            MiObjeto.status= responseCode

            if(responseCode == HttpURLConnection.HTTP_OK){
                val inStrRead = BufferedReader(InputStreamReader(urlConection.inputStream))

                val sb = StringBuffer("")
                var linea : String?

                while (inStrRead.readLine().also { linea = it } != null){
                    sb.append(linea)
                    break
                }
                inStrRead.close()
                MiObjeto.response = sb.toString()
            }else{
                MiObjeto.response = "null"
            }

        }catch (e: UnknownHostException){
            Log.e("UnknownHostException","$e")
        }catch (e: Exception){
            Log.e("Exception","$e")
        }
    return MiObjeto
    }

}