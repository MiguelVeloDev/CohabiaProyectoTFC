package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cohabiaproject.ui.theme.FondoTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerGrid(
    horas: Int,
    minutos: Int,
    onHorasChange: (Int) -> Unit,
    onMinutosChange: (Int) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TextField(
                value = horas.toString(),
                onValueChange = { newText ->
                    newText.toIntOrNull()?.let { onHorasChange(it) }
                },
                placeholder = { Text("0") },
                label = { Text("Hora") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            TextField(
                value = minutos.toString(),
                onValueChange = { newText ->
                    newText.toIntOrNull()?.let { onMinutosChange(it) }
                },
                label = { Text("Minutos") },
                placeholder = { Text("0") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }

    Spacer(modifier = Modifier.padding(20.dp))

    if (!isFocused && (horas > 23 || minutos > 59)) {
        Text(
            text = "Máximo de horas: 23, máximo de minutos: 59",
            fontSize = 15.sp,
            color = Color.Red
        )
    }
}
