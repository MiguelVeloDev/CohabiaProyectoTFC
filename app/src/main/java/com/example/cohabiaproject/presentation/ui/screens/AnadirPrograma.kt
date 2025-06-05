package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.components.SeleccionTiempo
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.coloresTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnadirPrograma(
    modifier: Modifier,
    navController: NavController,
    id : String
) {
    var nombrePrograma by remember { mutableStateOf("") }
    var seleccionHoras by remember { mutableStateOf(0) }
    var seleccionMinutos by remember { mutableStateOf(0) }
    val electrodomesticosViewModel: ElectrodomesticoViewModel = koinViewModel()
    val electrodomesticos = electrodomesticosViewModel.electrodomesticos.collectAsState()
    val electrodomestico = electrodomesticos.value.find { it.id == id }
    val programas = electrodomestico?.programas ?: emptyList()

    Scaffold(
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Nuevo programa",
                textoBoton = "Guardar",
                navController = navController,
                accion = {
                    val minutosTotales = seleccionHoras * 60 + seleccionMinutos

                    val nuevoPrograma = ProgramaElectrodomestico(
                        nombre = nombrePrograma,
                        minutos = minutosTotales
                    )
                    val nuevoElectrodomestico = electrodomestico!!.copy(
                        programas = (electrodomestico.programas + nuevoPrograma).toMutableList()
                    )


                    electrodomesticosViewModel.update(nuevoElectrodomestico)
                    navController.navigate(Screen.ListaElectrodomesticos.route)
                },
                enabled = nombrePrograma.isNotEmpty()
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(26.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lavadora),
                    contentDescription = "Electrodoméstico",
                    modifier = Modifier
                        .size(94.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = electrodomestico?.nombre ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                    Text(
                        text = electrodomestico?.tipo ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            TextField(
                value = nombrePrograma,
                onValueChange = { nuevoPrograma ->
                    if (nuevoPrograma.length <= 25) {
                        nombrePrograma = nuevoPrograma
                    }
                },                 placeholder = { Text("Nombre del programa") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = FondoTextField,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

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


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Programas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                items(programas) { programa ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = programa.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "${programa.minutos} min",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            IconButton(onClick = {  }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar programa",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
