package com.luisq.appagenciabienesraices

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class PanelPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel_principal)



        findViewById<Button>(R.id.btn_propiedades).setOnClickListener {
            startActivity(Intent(this, GestionPropiedades::class.java))
        }
        findViewById<Button>(R.id.btn_clientes).setOnClickListener {
            startActivity(Intent(this, GestionClientes::class.java))
        }
        findViewById<Button>(R.id.btn_agentes).setOnClickListener {
            startActivity(Intent(this, GestionAgentes::class.java))
        }



        actualizarUI()

        findViewById<Button>(R.id.btn_volver).setOnClickListener {
            cerrarSesion()
            finish() // Cierra HomeActivity
        }
    }

    private fun actualizarUI() {
        val txtUsuario = findViewById<TextView>(R.id.txt_usuario_actual)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && !user.email.isNullOrEmpty()) {
            txtUsuario.text = "Usuario: ${user.email}"
        } else {
            txtUsuario.text = "Usuario: Sin sesión"
        }
    }

    fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
    }
}

