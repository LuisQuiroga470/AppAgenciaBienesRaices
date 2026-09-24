package com.luisq.appagenciabienesraices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val txtNombre = findViewById<EditText>(R.id.txt_usuario)
        val txtPassword = findViewById<EditText>(R.id.txt_contraseña)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val usuario = txtNombre.text.toString().trim()
            val contraseña = txtPassword.text.toString().trim()

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
