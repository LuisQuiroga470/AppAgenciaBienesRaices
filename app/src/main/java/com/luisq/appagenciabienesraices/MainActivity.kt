package com.luisq.appagenciabienesraices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var analitica: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        analitica = Firebase.analytics




        val txtNombre = findViewById<EditText>(R.id.txt_usuario)
        val txtPassword = findViewById<EditText>(R.id.txt_contraseña)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val usuario = txtNombre.text.toString().trim()
            val contraseña = txtPassword.text.toString().trim()

            analitica.logEvent("clic_iniciar_sesion", null)

            if ((usuario == "luis" || usuario == "admin") && contraseña == "1234") {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, PanelPrincipal::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }



    }
}
