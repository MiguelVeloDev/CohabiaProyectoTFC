package com.example.cohabiaproject.presentation.ui.screens



import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.R
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TodosGastos(
    navController: NavController,
    finanzasViewmodel: FinanzasViewModel
) {
    val gastosEsteMes by finanzasViewmodel.todosGastosEsteMes.collectAsState(emptyList())

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {

        item {
            Image(
                painter = painterResource(id = R.drawable.grafico),
                contentDescription = "Grafico",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Text(
                text = "Este mes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0061D9),
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total", fontSize = 20.sp, color = Color.Gray)
                Text(
                    text = "${gastosEsteMes.sumOf { it.cantidad }}€",
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        }

        items(gastosEsteMes) { gasto ->
            Column {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 20.dp)
                        .clickable { navController.navigate("detalleGasto/${gasto.id}") }
                ) {
                    Text(
                        text = "${gasto.cantidad / gasto.usuarioPaga.size}€",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .padding(bottom = 20.dp)
                    ) {
                        Text(
                            text = gasto.concepto,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                        Text(
                            text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                .format(gasto.fecha.toDate()),
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ver todo",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0061D9),
                    fontSize = 15.sp,
                    modifier = Modifier.clickable { navController.navigate("listaFinanzas") }
                )
            }
        }
    }
}



@Composable
fun TOdosGastosItem(
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