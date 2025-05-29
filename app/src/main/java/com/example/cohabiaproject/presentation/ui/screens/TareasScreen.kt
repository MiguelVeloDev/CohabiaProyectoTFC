package com.example.cohabiaproject.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.DialogConfirmacion
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.TareaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.RojoTareas
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TareasScreen(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""
    val tareasViewModel: TareaViewModel = koinViewModel()
    val usuarioViewModel: UsuarioViewModel = koinViewModel()

    val misTareas = "Mis tareas"
    val todasTareas = "Tareas"
    val deuda = "Deudas"
    var textoSeleccionado by remember { mutableStateOf(misTareas) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Tareas",
                textoBoton = "Añadir tarea",
                navController = navController,
                accion = { navController.navigate("anadirTarea") },
                enabled = true
            )
        },
        bottomBar = {
            BottomNavBar(navController, selectedRoute = currentRoute)
        }
    ) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            // Pestañas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(misTareas, todasTareas, deuda).forEach { texto ->
                    Text(
                        text = texto,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { textoSeleccionado = texto }
                            .padding(vertical = 8.dp),
                        color = if (textoSeleccionado == texto) Color.Black else Color.Gray
                    )
                }
            }

            // Contenido dinámico
            when (textoSeleccionado) {
                misTareas -> {
                    MisTareas(
                        navController = navController,
                        tareasViewModel = tareasViewModel,
                        usuarioViewModel = usuarioViewModel
                    )
                }

                todasTareas -> {
                    TareasRecurrentes(
                        navController = navController,
                        tareasViewModel = tareasViewModel,
                        usuarioViewModel = usuarioViewModel
                    )
                }

                deuda -> {
                    Text("Aquí irán las deudas", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TareaItem(
    tarea: Tarea,
    navController: NavController,
    usuarioViewModel: UsuarioViewModel = koinViewModel()
    ,tareasViewModel: TareaViewModel = koinViewModel()
    ,eventoViewModel: EventoViewModel = koinViewModel()
) {
    var showDialogConfirmacion by remember { mutableStateOf(false) }
    var marcado by remember { mutableStateOf(false) }
    if (showDialogConfirmacion) {
        DialogConfirmacion(
            texto = "¿Marcar tarea como completada?",
            onDismiss = { showDialogConfirmacion = false },
            onConfirm ={
                marcado = true
                showDialogConfirmacion = false
                tareasViewModel.borrar(tarea.id)
                eventoViewModel.save(Evento(
                    tipo = "TAREA", contenido = eventoViewModel.generarMensaje("TAREA",tarea.contenido)))

            })}
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ){
        Text(
            text = tarea.contenido,
            color = Color.Black
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val fecha = tareasViewModel.generarTextoFecha(tarea)
            Text(
                text = fecha.first,
                color = if (fecha.second) Color.Red else Color.Black
            )
            IconButton(onClick = {
                showDialogConfirmacion = true
            }) {
                Icon(
                    imageVector = if (marcado) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Marcar producto",
                    tint = NaranjaPrincipal
                )
            }

        }
        HorizontalDivider(
            color = Color(0xFFDEDEDE),
            modifier = Modifier.padding(vertical = 1.dp)
        )
    }
}