package com.example.cohabiaproject.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.AzulTareas
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.RojoCompras
import com.example.cohabiaproject.ui.theme.VerdeNotas
import com.google.firebase.Timestamp

data class Evento(
    val id: String = "",
    val contenido: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val tipo : String = ""
) {
    constructor() : this("", "", Timestamp.now())
    companion object {
        fun generarMensaje(tipo: String, dato: String): String {
            return when (tipo) {
                "NOTA" -> "${Sesion.nombreUsuario} añadió la nota \"$dato\""
                "GASTO" -> "${Sesion.nombreUsuario} añadió un gasto de $dato €"
                "ELECTRODOMESTICO" -> "${Sesion.nombreUsuario} ha iniciado: $dato"
                "COMPRA" -> "${Sesion.nombreUsuario} ha comprado $dato productos"
                "TAREA" -> "${Sesion.nombreUsuario} ha completado: $dato"
                else -> "Evento desconocido"
            }
        }
    }
}

