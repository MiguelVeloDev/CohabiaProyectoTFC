package com.example.cohabiaproject.presentation.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import kotlinx.coroutines.flow.filter
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun DetallesGasto(
    modifier: Modifier = Modifier,
    navController: NavController,
    id: String
) {
    val usuarioViewModel: UsuarioViewModel = koinViewModel()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val finanzasViewmodel: FinanzasViewModel = koinViewModel()
    val gastos by finanzasViewmodel.finanzas.collectAsState(emptyList())
    val gasto = remember(gastos) { gastos.find { it.id == id } }
    val usuarios by usuarioViewModel.usuarios.collectAsState(emptyList())
    val listaUsuarios = remember(gasto, usuarios) {
        usuarios.filter { it.id in (gasto?.usuarioPaga ?: emptyList()) }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Detalles del Gasto",
                textoBoton = "",
                navController = navController,
                accion = {},
                enabled = false
            )
        },
        bottomBar = {
            BottomNavBar(navController, selectedRoute = currentRoute)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            gasto?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = it.concepto,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Divider()

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "Cantidad",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "${it.cantidad} €",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = AzulGastos
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                Text(
                                    text = "Fecha",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = dateFormat.format(it.fecha.toDate()),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

                        Divider()

                        Text(
                            text = "Pagado por",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            listaUsuarios.forEach { usuario ->
                                Text(
                                    text = "• ${usuario.nombre}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { navController.navigate("editarGasto/${it.id}") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AzulGastos
                        )
                    ) {
                        Text("Editar Gasto")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(
                        onClick = { finanzasViewmodel.borrar(it.id); navController.popBackStack() },
                        modifier = Modifier
                            .size(50.dp)
                            .background(color = Color.Red.copy(alpha = 0.1f), shape = MaterialTheme.shapes.medium)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar gasto",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

