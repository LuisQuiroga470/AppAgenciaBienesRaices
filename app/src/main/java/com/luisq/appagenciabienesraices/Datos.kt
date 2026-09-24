package com.luisq.appagenciabienesraices

// Comparte las listas y los contadores de ID entre pantallas; se pierden cuando termina el proceso de la app.
data class Registro(val id: Int, val datos: MutableList<String>)

object Datos {
    val propiedades = mutableListOf<Registro>()
    val clientes = mutableListOf<Registro>()
    val agentes = mutableListOf<Registro>()
    var siguientePropiedad = 1
    var siguienteCliente = 1
    var siguienteAgente = 1
}
