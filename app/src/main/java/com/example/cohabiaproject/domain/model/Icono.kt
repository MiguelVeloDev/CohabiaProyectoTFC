package com.example.cohabiaproject.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.twotone.DataArray
import androidx.compose.material.icons.twotone.Kitchen
import androidx.compose.material.icons.twotone.LocalLaundryService
import androidx.compose.material.icons.twotone.MonetizationOn
import androidx.compose.material.icons.twotone.NoteAlt
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material.icons.twotone.TaskAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.AzulTareas
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.RojoCompras
import com.example.cohabiaproject.ui.theme.VerdeNotas

data class Icono(
    val tipo: String,
    val icono: ImageVector,
    val color: Color
) {
    companion object {
        fun obtenerPorTipo(tipo: String): Icono {
            return Icono(
                tipo = tipo,
                icono = when (tipo) {
                    "NOTA" -> Icons.TwoTone.NoteAlt
                    "GASTO" -> Icons.TwoTone.MonetizationOn
                    "ELECTRODOMESTICO" -> Icons.TwoTone.LocalLaundryService
                    "COMPRA" -> Icons.TwoTone.ShoppingCart
                    "TAREA" -> Icons.TwoTone.TaskAlt
                    else -> Icons.TwoTone.DataArray
                },
                color = when (tipo) {
                    "NOTA" -> VerdeNotas
                    "GASTO" -> AzulGastos
                    "ELECTRODOMESTICO" -> MoradoElectrodomesticos
                    "COMPRA" -> RojoCompras
                    "TAREA" -> AzulTareas
                    else -> Color.Gray
                }
            )
        }
    }
}
