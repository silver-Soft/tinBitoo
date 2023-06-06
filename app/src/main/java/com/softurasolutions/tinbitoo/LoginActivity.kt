package com.softurasolutions.tinbitoo

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.softurasolutions.tinbitoo.RequestMethods.ClassConectionPOST
import com.softurasolutions.tinbitoo.databinding.ActivityLoginBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.util.concurrent.ExecutionException

class LoginActivity : AppCompatActivity() {
    private lateinit var  bindind: ActivityLoginBinding
    private var editor : SharedPreferences.Editor?=null

    val settingsConst = SettingsConst()

    var user: EditText?= null
    var paswd: EditText?= null
    var loginBtn: Button?= null
    var omitirLogin : TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindind = ActivityLoginBinding.inflate(layoutInflater)
        val view = bindind.root
        setContentView(view)

        user= bindind.editUser
        paswd = bindind.editPaswd
        loginBtn = bindind.login
        omitirLogin = bindind.tvOmitir
    }

    override fun onStart() {
        super.onStart()
        loginBtn?.setOnClickListener{
            login()
        }

        val prefs = applicationContext.getSharedPreferences("Preferences", MODE_PRIVATE)

        val usuario = prefs.getString("usuario","")
        val token = prefs.getString("token","")

        if (usuario != "" && token != ""){
            //val intent = Intent(applicationContext,)
            Log.e("Login"," Usuario?? $usuario")
        }else{
            Log.e("Login"," Inicia Sesión")
            editor = prefs.edit()
        }

    }
    private fun login() {
        Log.e("Login","Logueo")
        val usuario: String = user?.text.toString()
        val password: String = paswd?.text.toString()

        val postDataParamas = JSONObject()
        try{
            postDataParamas.put("usuario",usuario)
            postDataParamas.put("password", password)
            Log.e("postDataParamas","$postDataParamas")
        }catch (e: JSONException){
            Log.e("Json Exception","$e")
        }

        val servicioTask = ClassConectionPOST(
            settingsConst.servicio+"/api/usr/login", postDataParamas)
        try {
            val thread = Thread{
                if(settingsConst.hayConexion(applicationContext)){
                    val response = servicioTask.execute().get()
                    if(response?.status==200){

                        val jsonObject = JSONObject(response.response)
                        Log.e("jsonObject","$jsonObject")
                        val code: Int = jsonObject.getString("code").toInt()
                        val message = JSONTokener(jsonObject.getString("message")).nextValue() as JSONArray

                        if(code==200){
                            runOnUiThread{
                                Toast.makeText(applicationContext,message[0].toString(), Toast.LENGTH_LONG).show()
                            }

                            val data = jsonObject.getJSONObject("data")

                            val usuarioSistema = data.getJSONObject("usuario_sistema")
                            val msPersona = usuarioSistema.getJSONObject("ms_persona")

                            val nombreCompleto = msPersona.getString("nombre")+" "+msPersona.getString("paterno")+" "+msPersona.getString("materno")
                            val token = data.getString("token")

                            editor?.putString("id_usuario_sistema", usuarioSistema.getString("id_usuario_sistema"))
                            editor?.putString("usuario", usuarioSistema.getString("usuario"))
                            editor?.putString("nombreCompleto", nombreCompleto)
                            editor?.putString("correo",msPersona.getString("correo"))
                            editor?.putString("celular",msPersona.getString("celular"))
                            editor?.putString("token",token)
                            editor?.putString("img_user",msPersona.getString("imagen"))

                            editor?.apply()

                            runOnUiThread {
                                Toast.makeText(applicationContext,"Bienvenido $nombreCompleto", Toast.LENGTH_LONG).show()
                            }

                        }else{
                            runOnUiThread{
                                Toast.makeText(applicationContext,message[0].toString(), Toast.LENGTH_LONG).show()
                            }
                        }


                    }else{
                        runOnUiThread{
                            Toast.makeText(applicationContext,"Ocurrió un error, intente más tarde", Toast.LENGTH_LONG).show()
                        }
                    }


                }else{
                    runOnUiThread{
                        Toast.makeText(applicationContext,"No hay conexión", Toast.LENGTH_LONG).show()
                    }
                }
            }
            thread.start()

        }catch (e: ExecutionException){
            Log.e("ExecutionException","$e")
        }catch (e: JSONException){
            Log.e("JSONException","$e")
        }catch (e: InterruptedException){
            Log.e("InterruptedException","$e")
        }


    }
}