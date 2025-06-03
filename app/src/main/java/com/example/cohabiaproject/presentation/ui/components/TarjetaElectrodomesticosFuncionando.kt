package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.twotone.LocalLaundryService
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.R
import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel

@Composable
fun TarjetaFuncionando(
    modifier: Modifier = Modifier,
    electrodomesticosEnFuncionamiento: List<Electrodomestico>,
    navController: NavController
) {
    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("listaElectrodomesticos") },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "En funcionamiento",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF000000)
                )
            )
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val totalSlots = 3
                for (i in 0 until totalSlots) {
                    if (i < electrodomesticosEnFuncionamiento.size) {
                        ElectrodomesticoEnFuncionamientoCard(
                            electrodomestico = electrodomesticosEnFuncionamiento[i],
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        PlaceholderCardEnFuncionamiento(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ElectrodomesticoEnFuncionamientoCard(
    electrodomestico: Electrodomestico,
    modifier: Modifier = Modifier
) {


    val imagen = remember(electrodomestico.tipo) {
        Electrodomestico.obtenerImagen(electrodomestico.tipo)
    }

    Card(
        modifier = modifier
            .height(90.dp)
            .border(
                width = 1.dp,
                color = if (electrodomestico.esperandoFinalizar) MoradoElectrodomesticos else Color(0xFFB7B7B7),
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = electrodomestico.nombre,
                modifier = Modifier.size(45.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = electrodomestico.nombre,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = NaranjaPrincipal,
                    maxLines = 1
                )

            }
        }
    }

@Composable
fun PlaceholderCardEnFuncionamiento(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(90.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.TwoTone.LocalLaundryService,
            contentDescription = "Placeholder",
            tint = Color.LightGray,
            modifier = Modifier.size(24.dp)
        )
    }
}