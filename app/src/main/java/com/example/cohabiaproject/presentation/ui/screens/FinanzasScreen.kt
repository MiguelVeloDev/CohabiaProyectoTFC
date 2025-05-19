package com.example.cohabiaproject.presentation.ui.screens

import android.util.Log
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
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Text(
                        text = misGastos,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = { textoSeleccionado = misGastos })
                            .padding(10.dp),
                        color = if (textoSeleccionado == misGastos) Color.Blue else Color.Gray
                    )
                    Text(
                        text = todosLosGastos,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = {
                            textoSeleccionado = todosLosGastos
                        }).padding(10.dp),
                        color = if (textoSeleccionado == todosLosGastos) Color.Blue else Color.Gray
                    )
                    Text(
                        text = deuda,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = { textoSeleccionado = deuda })
                            .padding(10.dp),
                        color = if (textoSeleccionado == deuda) Color.Blue else Color.Gray
                    )

                }
            }


            when (textoSeleccionado) {
                misGastos -> {
                    MisGastos(navController = navController, finanzasViewmodel = finanzasViewModel)
                }
                todosLosGastos -> {
                    TodosGastos(navController = navController, finanzasViewmodel = finanzasViewModel)
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
    var cantidad by remember { mutableStateOf(gasto?.cantidad.toString() ?: "") }
    var fecha by remember { mutableStateOf(gasto?.fecha ?: Date()) }

    Scaffold(
        topBar = {
            NuevoElementoTopAppBar(

                titulo = "Gastos",
                textoBoton = "Añadir gasto",
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
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (gasto != null) {
                        TextField(
                            value = concepto,
                            onValueChange = { concepto = it }
                        )
                        TextField(
                            value = cantidad,
                            onValueChange = { cantidad = it }

                        )
                    }
                }
            }
        }
    }
}