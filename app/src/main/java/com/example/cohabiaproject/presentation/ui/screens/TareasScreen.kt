package com.example.cohabiaproject.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.TareaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TareasScreen(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""
    val tareasViewModel: TareaViewModel = koinViewModel()
    val usuarioViewModel: UsuarioViewModel = koinViewModel()

    val misTareas = "Mis tareas"
    val todasTareas = "Tareas"
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                listOf(misTareas, todasTareas).forEach { texto ->
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
    if (showDialogConfirmacion) {
        DialogConfirmacion(
            texto = "¿Marcar tarea como completada?",
            onDismiss = { showDialogConfirmacion = false },
            onConfirm ={
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
                    imageVector = Icons.Default.AddTask,
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