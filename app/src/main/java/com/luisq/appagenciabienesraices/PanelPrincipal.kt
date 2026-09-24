package com.luisq.appagenciabienesraices

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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
        findViewById<Button>(R.id.btn_volver).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
