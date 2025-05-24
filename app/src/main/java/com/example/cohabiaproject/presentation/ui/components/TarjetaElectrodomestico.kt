
package com.example.cohabiaproject.presentation.ui.components

import android.R.attr.textColorSecondary
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.R
import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel
@Composable
fun TarjetaElectrodomestico(
    electrodomestico: Electrodomestico,navController : NavController
) {
    val electrodomesticoViewModel: ElectrodomesticoViewModel = koinViewModel()
    val tiempoRestante =
        electrodomesticoViewModel.tiempoRestante(electrodomestico.id).collectAsState(initial = 0L).value
    val (h, m, s) = convertirMilisAHorasMinSeg(tiempoRestante)
    var expanded by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    val running = electrodomestico.usoProgramaActual?.pausado == false
    val img = remember(electrodomestico.tipo) {
        when (electrodomestico.tipo) {
            "Lavadora"     -> R.drawable.lavadora
            "Lavavajillas" -> R.drawable.lavavajillas
            "Horno"        -> R.drawable.horno
            "Secadora"     -> R.drawable.secadora
            "Aspirador"    -> R.drawable.robot_aspirador
            else           -> R.drawable.electrodomestico_generico
        }
    }

    if (showDialog) {
        DialogPrograma(
            programas = electrodomestico.programas.map { it.nombre },
            onDismiss = { showDialog = false },
            onProgramaSeleccionado = { programa ->
                electrodomestico.usoProgramaActual = UsoPrograma(
                    estado          = "en_ejecucion",
                    electrodomesticoId = electrodomestico.id,
                    programaId      = programa,
                    inicio          = System.currentTimeMillis(),
                    tiempoPausado   = 0L,
                    pausado         = false
                )
                electrodomestico.isRunning = true
                electrodomesticoViewModel.update(electrodomestico)
                electrodomesticoViewModel.saveUsoPrograma(electrodomestico.usoProgramaActual!!)
                electrodomesticoViewModel.iniciarContador(electrodomestico)
                showDialog = false
            }
        )
    }
    if (electrodomestico.usoProgramaActual != null) electrodomesticoViewModel.iniciarContador(electrodomestico)

    Card(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .padding(vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(img),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f).padding(end = 15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    electrodomestico.nombre, style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                electrodomestico.usoProgramaActual?.programaId?.let {
                    Text(it, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 12.dp)) {
                Text(
                    text = "${h}h ${m}m ${s}s",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        imageVector = if (running) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (running) "Pausar" else "Reanudar",
                        tint = MoradoElectrodomesticos,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                if (electrodomestico.usoProgramaActual == null) {
                                    showDialog = true
                                    return@clickable
                                }
                                val uso = electrodomestico.usoProgramaActual ?: return@clickable
                                if (!uso.pausado) {
                                    uso.pausado = true
                                    uso.timestampUltimaPausa = System.currentTimeMillis()
                                } else {
                                    val pausa = uso.timestampUltimaPausa?.let {
                                        System.currentTimeMillis() - it
                                    } ?: 0L
                                    uso.tiempoPausado += pausa
                                    uso.timestampUltimaPausa = null
                                    uso.pausado = false
                                }
                                electrodomesticoViewModel.update(electrodomestico)
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = "Parar",
                        tint = MoradoElectrodomesticos,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                electrodomestico.isRunning = false
                                electrodomestico.usoProgramaActual = null
                                electrodomesticoViewModel.update(electrodomestico)
                                electrodomesticoViewModel.resetContador(electrodomestico)
                            }
                    )
                }
            }

        }
            AnimatedVisibility(visible = expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3F3F3))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = { expanded = false; navController.navigate("anadirPrograma/${electrodomestico.id}") },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircleOutline,
                                tint = Color.Gray,
                                contentDescription = "Compartir nota"
                            )
                        }
                        IconButton(
                            onClick = { expanded = false },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = Color.Red,
                                contentDescription = "Borrar nota"
                            )
                        }
                    }
                }
            }
        }
    }

}

fun convertirMilisAHorasMinSeg(ms: Long): Triple<Int, Int, Int> {
    val total = ms / 1000
    val h = (total / 3600).toInt()
    val m = ((total % 3600) / 60).toInt()
    val s = (total % 60).toInt()
    return Triple(h, m, s)
}
