package com.example.cohabiaproject.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.FondoTextField

@Composable
fun DialogoGuardarFinanza(
    finanzasViewModel: FinanzasViewModel,
    onDismiss: () -> Unit,
    usuarioASaldarDeuda: String,
    cantidad: Double
) {
    var texto by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text(text = "Pagar deuda", color = AzulGastos) },
        text = {
            Column {
                TextField(
                    value = texto,
                    onValueChange = { texto = it },
                    label = { Text("Introduce el dato") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        unfocusedContainerColor = FondoTextField,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                finanzasViewModel.save(finanzasViewModel.crearFinanza(concepto = finanzasViewModel.deudaTexto, cantidad = texto.toDouble(), usuarioPaga = listOf(Sesion.userId), usuariosParticipan = listOf(usuarioASaldarDeuda), usuariosDeuda = listOf(usuarioASaldarDeuda)))
                onDismiss()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulGastos,
                    contentColor = Color.White
                ),
                enabled = texto.toDoubleOrNull() != null && texto.toDoubleOrNull()!! <= cantidad
                ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
                Log.d("TAG", "DialogoGuardarFinanza: $cantidad")
            }
        }
    )
}
