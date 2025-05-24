package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal

@Composable
fun DialogPrograma(
    programas: List<String>,
    onDismiss: () -> Unit,
    onProgramaSeleccionado: (String) -> Unit
) {
    var seleccionado by remember { mutableStateOf(programas.firstOrNull() ?: "") }

    AlertDialog(

        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onProgramaSeleccionado(seleccionado)
                    onDismiss()
                },
                enabled = seleccionado.isNotEmpty(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NaranjaPrincipal
            )) {
                Text("Seleccionar")
            }
        },
        dismissButton = {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Undo,
                    contentDescription = "Cancelar",
                    tint = Color.Gray
                )
            }
        },
        title = {
            Text(
                text = "Elige un programa",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            LazyColumn {
                itemsIndexed(programas) { index, programa ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { seleccionado = programa },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = seleccionado == programa,
                            onClick = { seleccionado = programa },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MoradoElectrodomesticos,
                                unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                        Text(
                            text = programa,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        AnimatedVisibility(
                            visible = seleccionado == programa,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Seleccionado",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}