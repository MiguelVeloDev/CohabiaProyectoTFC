package com.example.cohabiaproject.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.coloresTextField
import org.koin.androidx.compose.koinViewModel
import java.util.Date


@Composable
fun FinanzasScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val finanzasViewModel: FinanzasViewModel =
        koinViewModel()
    val gastosEsteMes by finanzasViewModel.gastosEsteMes.collectAsState(emptyList())
    val misGastos = "Mis gastos"
    val todosLosGastos = "Gastos conjuntos"
    val deuda = "Deudas"
    var textoSeleccionado by remember { mutableStateOf(misGastos) }
    val usuarioViewModel: UsuarioViewModel = koinViewModel()



    Scaffold(
        containerColor = Color.White,

        topBar = {
            NuevoElementoTopAppBar(

                titulo = "Gastos",
                textoBoton = "Añadir gasto",
                navController = navController,
                accion = { navController.navigate(Screen.SeleccionUsuarioGasto.route) },
                enabled = true

            )
        },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) })
    { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,

                ) {
                    Text(
                        text = misGastos,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable(onClick = { textoSeleccionado = misGastos })
                            .padding(10.dp),
                        color = if (textoSeleccionado == misGastos) Color.Black else Color.Gray
                    )
                    Text(
                        text = todosLosGastos,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable(onClick = {
                                textoSeleccionado = todosLosGastos
                            })
                            .padding(10.dp),
                        color = if (textoSeleccionado == todosLosGastos)Color.Black else Color.Gray
                    )
                    Text(
                        text = deuda,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable(onClick = { textoSeleccionado = deuda })
                            .padding(10.dp),
                        color = if (textoSeleccionado == deuda) Color.Black else Color.Gray
                    )

                }
            }


            when (textoSeleccionado) {
                misGastos -> {
                    MisGastos(navController = navController, finanzasViewmodel = finanzasViewModel)
                }
                todosLosGastos -> {
                    TodosGastos(navController = navController, finanzasViewModel = finanzasViewModel)
                }
                deuda -> {
                    MisDeudas(navController = navController, finanzasViewModel = finanzasViewModel, usuarioViewModel = usuarioViewModel)
            }

        }
    }
    }
}

@Composable
fun EditarGasto(
    modifier: Modifier = Modifier,
    navController: NavController,
    id: String
) {
    Log.d("id", id)

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val finanzasViewmodel: FinanzasViewModel = koinViewModel()
    val gastos by finanzasViewmodel.finanzas.collectAsState(emptyList())
    var gasto = remember(gastos) { gastos.find { it.id == id } }

    if (gasto == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Companion.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var concepto by remember { mutableStateOf(gasto?.concepto ?: "") }
    var cantidad by remember { mutableStateOf(gasto?.cantidad?.toString() ?: "") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(

                titulo = "Editar",
                textoBoton = "Guardar",
                navController = navController,
                accion = {
                    gasto?.concepto = concepto
                    gasto?.cantidad = cantidad.toDoubleOrNull() ?: 0.0

                    finanzasViewmodel.update(gasto)
                    navController.popBackStack()
                },
                enabled = true

            )
        },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) })
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                    if (gasto != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF1F1F1)
                        ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                           border = BorderStroke(1.dp, AzulGastos) ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(26.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextField(
                                    value = concepto,
                                    onValueChange = { concepto = it },
                                    colors = coloresTextField(),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                TextField(
                                    value = cantidad,
                                    onValueChange = { nuevoValor ->
                                        if (nuevoValor.toDoubleOrNull() != null || nuevoValor.isBlank()) {
                                            cantidad = nuevoValor
                                        }
                                    },
                                    singleLine = true,
                                    colors = coloresTextField()
                                )
                            }
                        }

                }
            }
        }
    }
}