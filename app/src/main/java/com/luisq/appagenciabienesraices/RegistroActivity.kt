package com.luisq.appagenciabienesraices

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
class RegistroActivity : AppCompatActivity() {

    private lateinit var oFirebaseAnalytics: FirebaseAnalytics
    private lateinit var oFirebaseAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    private val TAG = "EmailPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)




        oFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle().apply { putString("Mensaje", "Entro_al_registro") }
        oFirebaseAnalytics.logEvent("Formulario_registro", bundle)
        title = "Formulario Registro"

        // Instancia de FB Auth y Listener
        oFirebaseAuth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.btn_crearRegistro).setOnClickListener { crearRegistro() }
        findViewById<Button>(R.id.btn_volverRegistro).setOnClickListener { finish() }
    }
    override fun onStart() {
        super.onStart()
        if (oFirebaseAuth.currentUser != null) {
            finish()
        }
    }

    private fun crearRegistro() {
        val correo = findViewById<EditText>(R.id.txt_correo).text.toString()
        val pass = findViewById<EditText>(R.id.txt_contraseña).text.toString()

        oFirebaseAuth.createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")

                    val user = oFirebaseAuth.currentUser

                    val usuarioFirestore = hashMapOf(
                        "correo" to correo
                    )

                    db.collection("Usuarios")
                        .add(usuarioFirestore)
                        .addOnSuccessListener { docRef ->
                            Log.d(TAG, "Documento agregado con ID: ${docRef.id}")

                            Toast.makeText(
                                this,
                                "Usuario ha sido creado",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error cargando documento", e)
                        }

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                    Toast.makeText(
                        this,
                        "Falló la autenticación",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }



}


