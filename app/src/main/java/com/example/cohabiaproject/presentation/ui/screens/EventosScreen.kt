package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Icono
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.RojoCompras
import com.example.cohabiaproject.ui.theme.AzulTareas
import com.example.cohabiaproject.ui.theme.VerdeNotas
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventosScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val eventoViewModel: EventoViewModel = koinViewModel()
    val eventos by eventoViewModel.eventos.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = { MyTopAppBar(navController,"") },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        if(eventos.isEmpty()){
            ListaVaciaPlaceholder(
                icono = Icons.Default.NotificationsNone,
                texto = "notificaciones"
            )
            return@Scaffold
        }


        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            items(eventos) { evento ->
                EventoItem(evento = evento, navController = navController, eventoViewModel = eventoViewModel)
            }
        }
    }
}

@Composable
fun EventoItem(
    evento: Evento,
    navController: NavController,
    eventoViewModel: EventoViewModel,
) {
    val icono = Icono.obtenerPorTipo(evento.tipo)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

    ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 15.dp)
            ) {
                Icon(
                    imageVector = icono.icono,
                    contentDescription = "Icono",
                    tint = icono.color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = evento.contenido,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
