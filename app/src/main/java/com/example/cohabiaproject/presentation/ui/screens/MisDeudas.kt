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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.components.DialogoGuardarFinanza
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.LoadingAnimation
import kotlinx.coroutines.delay
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
    var usuarioSeleccionado by remember { mutableStateOf<String>("") }
    var cantidadSeleccionada by remember { mutableStateOf<Double>(0.0) }


    val mapDeudas = remember(usuarios, deudas) {
        finanzasViewModel.calcularDeudaTotal(usuarios)
    }
    var showDialog by remember { mutableStateOf(false) }

if(showDialog){
    DialogoGuardarFinanza(
        onDismiss = { showDialog = false },

        finanzasViewModel = finanzasViewModel,
        usuarioASaldarDeuda = usuarioSeleccionado,
        cantidad = cantidadSeleccionada
    )
}

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {


if(mapDeudas.isEmpty()){
    LoadingAnimation()
}else{
    Text(
        text = "Deudas",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color =AzulGastos,
    )

    Column(modifier = Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {


        Text(
            text =  "%.2f€".format(mapDeudas.values.sum()),
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = if (mapDeudas.values.sum() >=0) Color(0xFF3F9B42) else Color(0xFF9F0B00),
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
                        "$nombreUsuario te debe ${kotlin.math.abs(cantidad)}€"
                    } else if (cantidad < 0) {
                        "Debes ${"%.2f".format(kotlin.math.abs(cantidad))}€ a $nombreUsuario"
                    } else {
                        "No tienes deudas con $nombreUsuario"
                    }

                    Text(
                        text = texto,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulGastos,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                }
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            }
           for ((esDeudaDelUsuario,deuda) in finanzasViewModel.generarListaDeudasUsuario(usuarioId)){
               Row (
                   modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween

               ) {
                   Row {
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally,
                           modifier = Modifier.padding(end = 20.dp)
                       ) {
                           Text(
                               text = deuda.dia.toString(),
                               fontSize = 12.sp,
                               fontWeight = FontWeight.Medium,
                               color = Color.Gray,
                           )
                           Text(
                               text = finanzasViewModel.numeroAMes(deuda.mes),
                               fontSize = 12.sp,
                               fontWeight = FontWeight.Medium,
                               color = Color.Gray,
                           )
                       }
                       Text(
                           text = deuda.concepto,
                           fontSize = 16.sp,
                           fontWeight = FontWeight.Medium,
                           color = if (deuda.concepto == finanzasViewModel.deudaTexto) Color(
                               0xFF3F9B42
                           ) else Color.Black,
                           modifier = Modifier.padding(vertical = 10.dp)
                       )
                   }
                   if (deuda.concepto == finanzasViewModel.deudaTexto) {
                        Text(
                            text = "${"%.2f".format(deuda.cantidad)}€",
                            fontSize = 16.sp,
                            color = if (esDeudaDelUsuario) Color(0xFF258027) else Color(0xFF9F0B00)
                        )
                   } else {
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Text(
                               text = if (esDeudaDelUsuario)
                                   "Debes"
                               else
                                   "Te debe",
                               fontSize = 12.sp,
                               fontWeight = FontWeight.Medium,
                               color = Color.Black,
                           )
                           Text(
                               text = "${"%.2f".format(deuda.cantidad)}€",
                               fontSize = 16.sp,
                               color = if (esDeudaDelUsuario)
                                   Color(0xFF9F0B00)
                               else
                                   Color(0xFF3F9B42)

                           )
                       }
                   }
               }
        }
            if(cantidad < 0) {
                Button(
                    onClick = {usuarioSeleccionado = usuarioId;cantidadSeleccionada = -cantidad; showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulGastos
                    )
                ) {
                    Text(text = "Liquidar deudas")
                }
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
}