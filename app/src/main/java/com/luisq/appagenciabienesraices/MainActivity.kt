package com.luisq.appagenciabienesraices

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var analitica: FirebaseAnalytics
    private lateinit var googleSignInClient: GoogleSignInClient

    
    private val googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null && account.idToken != null) {
                autenticarConFirebaseGoogle(account.idToken!!)
            }
        } catch (e: ApiException) {
            Log.w(TAG, "Error Google Sign-In status code: ${e.statusCode}", e)
            Toast.makeText(this, "Error Google Sign-In código: ${e.statusCode}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.w(TAG, "Error Google Sign-In", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        analitica = Firebase.analytics
        mAuth = FirebaseAuth.getInstance()
        title = "Inicio de sesión"

        // Configuración básica de Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val txtCorreo = findViewById<EditText>(R.id.txt_correo)
        val txtPassword = findViewById<EditText>(R.id.txt_contraseña)
        val btnLogin = findViewById<Button>(R.id.btniniciarSesion)
        val btnIrRegistro = findViewById<Button>(R.id.btn_Registrar)
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnMicrosoft = findViewById<Button>(R.id.btnMicrosoft)

        btnLogin.setOnClickListener {
            ingresar(txtCorreo.text.toString(), txtPassword.text.toString())
        }

        btnIrRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnGoogle.setOnClickListener {
            //  mostrar el selector de cuentas siempre
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                googleLauncher.launch(signInIntent)
            }
        }

        btnMicrosoft.setOnClickListener {
            ingresarConMicrosoft()
        }
    }

    private fun ingresarConMicrosoft() {
        val provider = OAuthProvider.newBuilder("microsoft.com")
        provider.addCustomParameter("prompt", "login")

        val pendingResultTask = mAuth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask.addOnSuccessListener {
                val intent = Intent(this, PanelPrincipal::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            mAuth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    val intent = Intent(this, PanelPrincipal::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error Microsoft Sign-In", e)
                    Toast.makeText(this, "Error Microsoft: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun autenticarConFirebaseGoogle(idToken: String) {
        val credencial = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credencial)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, PanelPrincipal::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Falló la autenticación con Firebase", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun ingresar(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this, PanelPrincipal::class.java)
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
