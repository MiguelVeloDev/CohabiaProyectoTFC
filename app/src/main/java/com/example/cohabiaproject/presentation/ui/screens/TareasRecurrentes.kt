package com.example.cohabiaproject.presentation.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.presentation.ui.components.DialogConfirmacion
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.TareaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.RojoTareas
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TareasRecurrentes(
    navController: NavController,
    tareasViewModel: TareaViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val tareas by tareasViewModel.tareasEsteMes.collectAsState()
    val tareasRecurrentes by tareasViewModel.tareasRecurrentes.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

            items(tareasRecurrentes) { tarea ->
                TareaRecurrenteItem(tarea = tarea, navController = navController, usuarioViewModel = usuarioViewModel)
            }
        }
    }





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TareaRecurrenteItem(
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
            texto = "¿Realizar tarea?",
            onDismiss = { showDialogConfirmacion = false },
            onConfirm ={
                marcado = true
                showDialogConfirmacion = false
                eventoViewModel.save(Evento(
                    tipo = "TAREA", contenido = eventoViewModel.generarMensaje("TAREA",tarea.contenido)))

            })}
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = tarea.contenido,
                color = Color.Black
            )

            IconButton(onClick = {
                showDialogConfirmacion = true
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
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