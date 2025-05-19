package com.example.cohabiaproject.presentation.ui.screens



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun DetallesGasto(
    modifier: Modifier = Modifier,
    navController: NavController,
    id: String
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val finanzasViewmodel: FinanzasViewModel = koinViewModel()
    val gastos by finanzasViewmodel.finanzas.collectAsState(emptyList())
    val gasto = remember(gastos) { gastos.find { it.id == id } }




    Scaffold(
        topBar = {
            NuevoElementoTopAppBar(

                titulo = "Gastos",
                textoBoton = "Añadir gasto",
                navController = navController,
                accion = { navController.navigate("nuevoGasto") },
                enabled = true

            )},
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) })
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier.padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (gasto != null) {
                            "${gasto.concepto}€"
                        } else "",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (gasto != null) {
                                gasto.cantidad.toString()
                            } else "",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = if (gasto != null) {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                dateFormat.format(gasto.fecha.toDate())
                            } else "",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (gasto != null) {
                                    navController.navigate("editarGasto/${gasto.id}")
                                }
                            }
                        ) {
                            Text("Editar")
                        }

                        }
                     }
                }
            }



        }

        }




