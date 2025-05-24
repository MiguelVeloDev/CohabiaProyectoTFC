package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MisDeudas(
navController: NavController,
finanzasViewModel: FinanzasViewModel,
usuarioViewModel: UsuarioViewModel
) {

    val usuarios by usuarioViewModel.usuarios.collectAsState(initial = emptyList())
    val deudas by finanzasViewModel.deudas.collectAsState()

    val mapDeudas = finanzasViewModel.calcularDeudaTotal(usuarios)


Column(modifier = Modifier.padding(horizontal = 16.dp)) {



    Text(
        text = "Lista",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color =AzulGastos,
    )
    Row(modifier = Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = "Total",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding()
        )
        Text(
            text = "${mapDeudas.values.sum()}€",
            fontStyle = FontStyle.Italic,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
    HorizontalDivider(thickness = 2.dp, color = Color.Gray)
}
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mapDeudas.entries.toList()) { (usuarioId, cantidad) ->
            val nombreUsuario = usuarios.find { it.id == usuarioId }?.nombre ?: ""

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable { }
            ) {
                Row {


                    val texto = if (cantidad > 0) {
                        "Debes ${"%.2f".format(cantidad)}€ a $nombreUsuario"
                    } else if (cantidad < 0) {
                        "$nombreUsuario te debe ${"%.2f".format(-cantidad)}€"
                    } else {
                        "No tienes deudas con $nombreUsuario"
                    }

                    Text(
                        text = texto,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

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
