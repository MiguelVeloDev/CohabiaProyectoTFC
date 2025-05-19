package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuRapido() {
    val mostrarHoja = remember { mutableStateOf(false) }
    val estadoHoja = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { mostrarHoja.value = false },
        sheetState = estadoHoja,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OpcionRapida("Añadir gasto", Icons.Default.Money) { }
            OpcionRapida("Añadir receta", Icons.Default.Restaurant) { }
            OpcionRapida("Nuevo evento", Icons.Default.Event) {  }
            OpcionRapida("Añadir compra", Icons.Default.ShoppingCart) { }
            OpcionRapida("Notificación", Icons.Default.Notifications) {  }
        }
    }
}

@Composable
fun OpcionRapida(texto: String, icono: ImageVector, alPulsar: () -> Unit) {
    ListItem(
        leadingContent = { Icon(icono, contentDescription = null) },
        headlineContent = { Text(texto) },
        modifier = Modifier.clickable { alPulsar() }
    )
}
