package com.luisq.appagenciabienesraices

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GestionPropiedades : AppCompatActivity() {
    private val registros = Datos.propiedades
    private val campos = listOf("Tipo", "Dirección", "Comuna / ciudad", "Superficie del terreno (m²)", "Dormitorios", "Baños", "Precio de venta", "Precio de arriendo", "Estado", "Descripción")
    private val numeros = listOf("Superficie del terreno (m²)", "Precio de venta", "Precio de arriendo")
    private lateinit var lista: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_propiedades)
        lista = findViewById(R.id.listaRegistros)
        findViewById<Button>(R.id.btnRegresar).setOnClickListener { finish() }
        findViewById<FloatingActionButton>(R.id.btnAgregar).setOnClickListener {
            abrirFormulario(null)
        }
        mostrarRegistros()
    }

    private fun mostrarRegistros() {
        lista.removeAllViews()
        findViewById<TextView>(R.id.txtSinRegistros).visibility =
            if (registros.isEmpty()) View.VISIBLE else View.GONE

        for (registro in registros) {
            val tarjeta = layoutInflater.inflate(R.layout.tarjeta_propiedades, lista, false)
            tarjeta.findViewById<TextView>(R.id.txtId).text = "ID: ${registro.id}"
            tarjeta.findViewById<TextView>(R.id.txtDatos).text =
                campos.indices.joinToString("\n") { "${campos[it]}: ${registro.datos[it]}" }
            tarjeta.findViewById<Button>(R.id.btnEditar).setOnClickListener {
                abrirFormulario(registro)
            }
            tarjeta.findViewById<Button>(R.id.btnEliminar).setOnClickListener {
                registros.remove(registro)
                mostrarRegistros()
            }
            lista.addView(tarjeta)
        }
    }

    private fun abrirFormulario(registro: Registro?) {
        val formulario = layoutInflater.inflate(R.layout.formulario_propiedades, null)
        val entradas = listOf<View>(
            formulario.findViewById(R.id.tipo),
            formulario.findViewById(R.id.direccion),
            formulario.findViewById(R.id.comuna),
            formulario.findViewById(R.id.superficie),
            formulario.findViewById(R.id.dormitorios),
            formulario.findViewById(R.id.banos),
            formulario.findViewById(R.id.precioVenta),
            formulario.findViewById(R.id.precioArriendo),
            formulario.findViewById(R.id.estado),
            formulario.findViewById(R.id.descripcion)
        )
        formulario.findViewById<TextView>(R.id.txtTituloFormulario).text =
            if (registro == null) "Agregar registro" else "Editar registro"
        formulario.findViewById<TextView>(R.id.txtIdFormulario).text =
            if (registro == null) "ID: se genera al guardar" else "ID: ${registro.id}"

        if (registro != null) {
            for ((posicion, entrada) in entradas.withIndex()) {
                if (entrada is Spinner) {
                    for (opcion in 0 until entrada.count) {
                        if (entrada.getItemAtPosition(opcion).toString() == registro.datos[posicion]) {
                            entrada.setSelection(opcion)
                        }
                    }
                } else if (entrada is EditText) {
                    entrada.setText(registro.datos[posicion])
                }
            }
        }

        val ventana = AlertDialog.Builder(this).setView(formulario).create()
        formulario.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            ventana.dismiss()
        }
        formulario.findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val valores = mutableListOf<String>()
            var valido = true
            for ((posicion, entrada) in entradas.withIndex()) {
                var valor = if (entrada is Spinner) entrada.selectedItem.toString()
                    else (entrada as EditText).text.toString().trim()
                val campo = campos[posicion]
                var error: String? = null
                if (valor.isBlank()) {
                    error = "Completa este campo"
                } else if (campo in numeros) {
                    val numero = valor.replace(',', '.').toDoubleOrNull()
                    if (numero == null || !numero.isFinite() || numero < 0) {
                        error = "Ingresa un número válido mayor o igual a cero"
                    }
                } else if (campo == "Dormitorios" || campo == "Baños") {
                    if (valor.equals("N/A", true)) {
                        valor = "N/A"
                    } else if (valor.toIntOrNull() == null || valor.toInt() < 0) {
                        error = "Ingresa una cantidad entera o N/A"
                    }
                }
                if (entrada is EditText) {
                    entrada.error = error
                    if (error != null) {
                        if (valido) entrada.requestFocus()
                        valido = false
                    }
                }
                valores.add(valor)
            }
            if (valido) {
                if (registro == null) {
                    registros.add(Registro(Datos.siguientePropiedad++, valores))
                } else {
                    registro.datos.clear()
                    registro.datos.addAll(valores)
                }
                mostrarRegistros()
                ventana.dismiss()
            }
        }
        ventana.show()
    }
}
