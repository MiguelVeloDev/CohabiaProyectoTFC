package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TimePickerGrid
import com.example.cohabiaproject.ui.theme.FondoTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoPrograma(
    navController: NavController,
    nombre: String,
    tipo: String) {
    var nombrePrograma by remember { mutableStateOf("") }
    var seleccionHoras by remember { mutableStateOf(0) }
    var seleccionMinutos by remember { mutableStateOf(0) }
    val electrodomesticosViewModel: ElectrodomesticoViewModel =
        koinViewModel()


    Scaffold(
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Nuevo programa",
                textoBoton = "Guardar",
                navController = navController,
                accion = {
                    val minutosTotales = seleccionHoras * 60 + seleccionMinutos

                    val nuevoElectrodomestico = Electrodomestico(
                        nombre = nombre,
                        tipo = tipo,
                        programas = listOf(
                            ProgramaElectrodomestico(
                                nombre = nombrePrograma,
                                minutos = minutosTotales
                            )
                        ),
                        isRunning = false
                    )

                    electrodomesticosViewModel.save(nuevoElectrodomestico)
                    navController.navigate(Screen.ListaElectrodomesticos.route)

                },
                enabled =  nombrePrograma.isNotEmpty()
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.lavadora),
                contentDescription = "Electrodoméstico",
                modifier = Modifier
                    .size(94.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = Bold,
                color = Color.Black,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Rellena el nombre del programa, selecciona cuanto tiempo dura y pulsa en + para añadir un nuevo programa. Puedes añadir cuantos quieras.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            TextField(
                value = nombrePrograma,
                onValueChange = { nombrePrograma = it },
                placeholder = { Text("Nombre Programa") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            TimePickerGrid(
                horas = seleccionHoras,
                minutos = seleccionMinutos,
                onHorasChange = { seleccionHoras = it },
                onMinutosChange = { seleccionMinutos = it }
            )

            Spacer(modifier = Modifier.padding(40.dp))

           }
    }
}