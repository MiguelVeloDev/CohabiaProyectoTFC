package com.example.cohabiaproject.presentation.ui.screens



import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoneyOff
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
import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.LoadingAnimation
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TodosGastos(
    navController: NavController,
    finanzasViewModel: FinanzasViewModel
) {
    val gastosEsteMesSinFiltrar by finanzasViewModel.todosGastosEsteMes.collectAsState(emptyList())
    val gastosEsteMes by remember(gastosEsteMesSinFiltrar, finanzasViewModel.deudaTexto) {
        mutableStateOf(
            gastosEsteMesSinFiltrar.filter { it.concepto != finanzasViewModel.deudaTexto }
        )
    }
    val total = gastosEsteMes.sumOf { it.cantidad }
    val totalRedondeado = String.format(Locale.US, "%.2f", total)

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {

        item {

            Text(
                text = "Este mes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AzulGastos,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {


                    Text(
                        text = "$totalRedondeado€",
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    Text(
                        text = "Total",
                        fontSize = 20.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding()
                    )
                }
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        }
        item {
            if (gastosEsteMes.isEmpty()) {
                ListaVaciaPlaceholder(
                    icono = Icons.Default.MoneyOff,
                    texto = "gastos este mes"
                )
                return@item
            }
        }

        items(gastosEsteMes) { gasto ->
            GastoItem(gasto = gasto, navController = navController)
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
                    color = AzulGastos,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable { navController.navigate("listaFinanzas") }
                )
            }
        }
    }
}

