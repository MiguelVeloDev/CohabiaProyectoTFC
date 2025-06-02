package com.example.cohabiaproject.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cohabiaproject.R
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.AzulTareas
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.RojoCompras
import com.example.cohabiaproject.ui.theme.VerdeNotas


data class Electrodomestico(
    val id: String = "",
    val nombre: String = "",
    val tipo: String = "",
    var programas: MutableList<ProgramaElectrodomestico> = mutableListOf(),
    var isRunning: Boolean = false,
    var usoProgramaActual: UsoPrograma? = null,
    val electrodomesticoSinProgramas : Boolean,
    var esperandoFinalizar : Boolean = false

) {
    constructor() : this(id = "", nombre = "", tipo = "", programas = mutableListOf(), isRunning = false, electrodomesticoSinProgramas = false, esperandoFinalizar = false)
    companion object {
        fun obtenerImagen(tipo: String): Int {
            return when (tipo) {
                "Lavadora" -> R.drawable.lavadora
                "Secadora" -> R.drawable.secadora
                "Lavavajillas" -> R.drawable.lavavajillas
                "Horno" -> R.drawable.horno
                "Aspirador" -> R.drawable.robot_aspirador
                "Otros" -> R.drawable.electrodomestico_generico
                else -> R.drawable.electrodomestico_generico


            }
        }
    }
}