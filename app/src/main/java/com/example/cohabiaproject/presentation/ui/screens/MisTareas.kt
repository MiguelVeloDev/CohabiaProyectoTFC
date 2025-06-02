package com.example.cohabiaproject.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.viewmodel.TareaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulTareas
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MisTareas(
    navController: NavController,
    tareasViewModel: TareaViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    var showDialogConfirmacion by remember { mutableStateOf(false) }
    val tareas by tareasViewModel.tareasEsteMes.collectAsState()
    val hoy = LocalDate.now()


    val tareasCaducadas = tareas.filter { tarea ->
        val fechaTarea = LocalDate.of(tarea.año!!, tarea.mes!!, tarea.dia!!.toInt())
        fechaTarea.isBefore(hoy)
    }


    val tareasHoy = tareas.filter { tarea ->
        tarea.dia == hoy.dayOfMonth.toString() &&
                tarea.mes == hoy.monthValue &&
                tarea.año == hoy.year
    }

    val tareasMesSinHoy = tareas.filter { tarea ->
         (tarea !in tareasHoy && tarea !in tareasCaducadas)
    }


    if(tareasHoy.isEmpty() && tareasCaducadas.isEmpty() && tareasMesSinHoy.isEmpty()){
        ListaVaciaPlaceholder(
            icono = Icons.Default.Checklist,
            texto = "tareas"
        )
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        if (tareasCaducadas.isNotEmpty()) {
            item {
                Text(text = "Atrasadas", fontSize = 22.sp,
                    color = AzulTareas,
                    fontWeight = FontWeight.Bold
                )
            }
            items(tareasCaducadas) { tarea ->
                TareaItem(tarea = tarea, navController = navController, usuarioViewModel = usuarioViewModel)
            }
        }

        if (tareasHoy.isNotEmpty()) {
            item {
                Text(text = "Hoy", fontSize = 22.sp,
                    color = AzulTareas,
                    fontWeight = FontWeight.Bold
                )
            }
            items(tareasHoy) { tarea ->
                TareaItem(tarea = tarea, navController = navController, usuarioViewModel = usuarioViewModel)
            }
        }

        if (tareasMesSinHoy.isNotEmpty()) {
            item {
                Text(text = "Este mes", modifier = Modifier.padding(top = 16.dp), fontSize = 22.sp, fontWeight = FontWeight.Bold,
                    color = AzulTareas)
            }
            items(tareasMesSinHoy) { tarea ->
                TareaItem(tarea = tarea, navController = navController, usuarioViewModel = usuarioViewModel)
            }
        }
    }
}
