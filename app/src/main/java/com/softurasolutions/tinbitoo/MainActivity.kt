package com.softurasolutions.tinbitoo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.softurasolutions.tinbitoo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = applicationContext.getSharedPreferences("Preferences", MODE_PRIVATE)
        token = prefs.getString("token","").toString()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,R.id.navigation_productos,
                R.id.navigation_promociones, R.id.navigation_perfil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_action_bar, menu)
        val itemlogout : MenuItem = menu.findItem(R.id.logout)

        itemlogout.isVisible = false

        if(token != ""){
            itemlogout.isVisible= true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.options -> {

                true
            }
            R.id.favs -> {

                true
            }
            R.id.logout -> {
                showDialogLogout()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showDialogLogout() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Cerrar sesión")
        builder.setMessage("¿Estas seguro que desea cerrar sesión?")

        builder.setPositiveButton("Aceptar"){_,_ ->
            val prefs1 = getSharedPreferences("Preferences", 0)
            val editor = prefs1.edit()

            editor.clear()
            editor.apply()

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("Cancelar"){_,_ ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Salir de TinBitoo")
        builder.setMessage("¿Estas seguro que deseas salir de TinBitoo?")

        builder.setPositiveButton("Aceptar"){_,_ ->
            finish()
        }
        builder.setNegativeButton("Cancelar"){_,_ ->
        }

        val dialog = builder.create()
        dialog.show()
    }


}