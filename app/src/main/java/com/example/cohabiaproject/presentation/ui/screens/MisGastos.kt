package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.R
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.components.MiBarChart
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MisGastos(
navController: NavController,
finanzasViewmodel: FinanzasViewModel,
) {

    val gastosEsteMes by finanzasViewmodel.gastosEsteMes.collectAsState(emptyList())

LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
    item{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(Color(0xFFEFF0F5)),
            contentAlignment = Alignment.Center,

        ) {
            MiBarChart(
                datos = mapOf(
                    "Enero" to 100f,
                    "Febrero" to 200f,
                    "Marzo" to 150f,
                    "Abril" to 300f,
                    "Mayo" to 250f,
                )
            )
        }
    Text(
        text = "Este mes",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color =AzulGastos,
        modifier = Modifier.padding( top = 20.dp,bottom = 10.dp)
    )
    Column(modifier = Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
        {


        Text(
            text = "${gastosEsteMes.sumOf { it.cantidad }}€",
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
        Column {
        Column(modifier = Modifier
            .padding( horizontal = 10.dp).padding(top = 20.dp)
            .clickable(onClick = { navController.navigate("detalleGasto/${gasto.id}") })) {
            Text(
                text = "${gasto.cantidad/gasto.usuarioPaga.size}€",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp).padding(bottom = 20.dp)

            ) {
                Text(
                    text = "${gasto.concepto}",
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                        gasto.fecha.toDate()
                    ),
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        }
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        }
        }


}
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
        modifier = Modifier.clickable { navController.navigate("listaFinanzas") },
        fontSize = 15.sp,
    )
}


}
