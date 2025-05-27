package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.ui.theme.AnimacionBotonCentral
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.VerdeNotas


@Composable
fun BottomNavBar(navController: NavController, selectedRoute: String) {
    var color by remember { mutableStateOf(NaranjaPrincipal) }
    val mostrarHoja = remember { mutableStateOf(false) }

    when (selectedRoute) {
        "main" -> color = NaranjaPrincipal
        "listaElectrodomesticos" -> color = MoradoElectrodomesticos
        "notas" -> color = Color.Green
        "finanzas" -> color = AzulGastos
    }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            NavigationBarItem(
                selected = selectedRoute == "main",
                onClick = { navController.navigate(Screen.Main.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.House,
                        contentDescription = "Inicio"
                    )
                },
                label = { Text("Inicio") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = color,
                    selectedTextColor = color,
                    indicatorColor = Color.Transparent

                )
            )

            NavigationBarItem(
                selected = selectedRoute == "listaElectrodomesticos",
                onClick = { navController.navigate("listaElectrodomesticos") },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.LocalLaundryService,
                        contentDescription = "Electrodomésticos"
                    )
                },
                label = { Text("Elec.") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = color,
                    selectedTextColor = color,
                    indicatorColor = Color.Transparent
                )
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = { mostrarHoja.value = true },
                    containerColor = if (selectedRoute == "main") color else Color.Black,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Filled.Add, "Abrir menú", tint = Color.White)
                }
            }

            NavigationBarItem(
                selected = selectedRoute == "notas",
                onClick = { navController.navigate("notas") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.NoteAlt,
                        contentDescription = "Notas"
                    )
                },
                label = { Text("Notas") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeNotas,
                    selectedTextColor = VerdeNotas,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = selectedRoute == "finanzas",
                onClick = { navController.navigate("finanzas") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = "Finanzas"
                    )
                },
                label = { Text("Finanzas") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = color,
                    selectedTextColor = color,
                    indicatorColor = Color.Transparent
                )
            )
        }
        if (mostrarHoja.value) {
            MenuRapido(mostrarHoja = mostrarHoja, navController)
        }
    }
}