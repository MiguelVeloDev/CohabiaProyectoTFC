package com.example.cohabiaproject.presentation.ui.screens


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DevicesOther
import androidx.compose.material.icons.filled.Dry
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Microwave
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.TextFieldDefaults

import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme


class NuevoElectrodomestico : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CohabiaProjectTheme {

                Login(modifier = Modifier, navController = rememberNavController())
            }
        }
    }
}

@Composable
fun NuevoElectrodomestico(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var seleccionado by remember { mutableStateOf<String?>(null) }
    var nombreElectrodomestico by remember { mutableStateOf("") }


    Scaffold(
        topBar = {  NuevoElementoTopAppBar(
            titulo = "Nuevo elec.",
            textoBoton = "Siguiente",
            navController = navController,
            accion = { navController.navigate("nuevoPrograma/$nombreElectrodomestico/$seleccionado")},
            enabled = if (seleccionado != null && !nombreElectrodomestico.isEmpty())  true else false)
        })
     { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val electrodomesticos = listOf(
                "Lavadora" to Icons.Default.LocalLaundryService,
                "Secadora" to Icons.Default.Dry,
                "Lavavajillas" to Icons.Default.Kitchen,
                "Horno" to Icons.Default.Microwave,
                "Aspirador" to Icons.Default.CleaningServices,
                "Otros" to Icons.Default.DevicesOther
            )



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                electrodomesticos.chunked(3).forEach { fila ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        fila.forEach { (nombre, icono) ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(2.dp)
                                    .clickable {
                                        seleccionado = if (seleccionado == nombre) null else nombre
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = icono,
                                            contentDescription = nombre,
                                            tint = if (seleccionado == nombre) Color(0xFFFFA500) else Color.Gray,
                                            modifier = Modifier.size(42.dp)
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(text = nombre, color = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(30.dp))



                    TextField(
                        value = nombreElectrodomestico,
                        onValueChange = { nombreElectrodomestico = it },
                        placeholder = { Text("Nombre electrodoméstico") },
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

                }
            }
        }
    }





@Preview(showBackground = true)
@Composable
fun NuevoElectrodomesticoPreview() {
    CohabiaProjectTheme {

        NuevoElectrodomestico(modifier = Modifier, navController = rememberNavController())
    }
}

