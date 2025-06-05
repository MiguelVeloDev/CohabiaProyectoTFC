package com.example.cohabiaproject.presentation.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.coloresTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun DialogSelectorTiempo(
    onDismiss: () -> Unit,
    onTimeSelected: (Int) -> Unit
) {
    var seleccionHoras by remember { mutableStateOf(0) }
    var seleccionMinutos by remember { mutableStateOf(0) }
    val tiempoSeleccionado = seleccionHoras * 60 + seleccionMinutos

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onTimeSelected(tiempoSeleccionado)
                    onDismiss()
                },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NaranjaPrincipal
                )
            ) {
                Text("Seleccionar")
            }
        },
        dismissButton = {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Filled.Undo,
                    contentDescription = "Cancelar",
                    tint = Color.Gray
                )
            }
        },
        title = {
            Text(
                text = "Introduce cuanto tiempo va a estar en funcionamiento",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = if (seleccionHoras == 0) "" else seleccionHoras.toString(),
                    onValueChange = { nuevoValor ->
                        val numero = nuevoValor.filter { it.isDigit() }
                        seleccionHoras = if (numero.isEmpty()) 0 else numero.toInt().coerceIn(0, 23)
                    },
                    label = { Text("Horas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true,
                    colors = coloresTextField()
                )
                TextField(
                    value = if (seleccionMinutos == 0) "" else seleccionMinutos.toString(),
                    onValueChange = { nuevoValor ->
                        val numero = nuevoValor.filter { it.isDigit() }
                        seleccionMinutos = if (numero.isEmpty()) 0 else numero.toInt().coerceIn(0, 59)
                    },
                    label = { Text("Minutos") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    singleLine = true,
                    colors = coloresTextField()
                )
            }

        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}
