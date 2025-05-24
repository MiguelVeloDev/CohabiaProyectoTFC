package com.example.cohabiaproject.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.NotaViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.VerdeNotas
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

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
    val textColorSecondary = Color.Black.copy(alpha = 0.6f)
    val textColorDisabled = Color.Black.copy(alpha = 0.38f)

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
                    imageVector = when (evento.tipo) {
                        "NOTA" -> Icons.Default.NoteAlt
                        "GASTO" -> Icons.Default.MonetizationOn
                        "ELECTRODOMESTICO" -> Icons.Default.Kitchen
                        else -> Icons.Default.MoreHoriz
                    },
                    contentDescription = "Icono",
                    tint = when (evento.tipo) {
                        "NOTA" -> VerdeNotas
                        "GASTO" -> AzulGastos
                        "ELECTRODOMESTICO" -> NaranjaPrincipal
                        else -> Color.Black
                    },
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
