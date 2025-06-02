package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.twotone.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Icono
import com.example.cohabiaproject.presentation.ui.screens.EventoItem
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
@Composable
fun UltimosEventosComponente(
    eventos: StateFlow<List<Evento>>,
    navController: NavController
) {
    val listaEventos = eventos.collectAsState().value

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD)),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (listaEventos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ListaVaciaPlaceholder(
                        icono = Icons.TwoTone.NotificationsNone,
                        texto = "eventos"
                    )
                }
            } else {
                val eventosAMostrar = listaEventos.toMutableList()

                while (eventosAMostrar.size < 4) {
                    eventosAMostrar.add(
                        Evento(tipo = "", contenido = "...")
                    )
                }

                eventosAMostrar.forEach { evento ->
                    EventoItem(evento = evento)

                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                }

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { navController.navigate("eventos") }
                ) {
                    Text(
                        text = "Ver todos",
                        color = NaranjaPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EventoItem(evento: Evento) {
    val icono = Icono.obtenerPorTipo(evento.tipo)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icono.icono,
            contentDescription = "Icono de evento",
            tint = icono.color,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = evento.contenido,
            fontSize = 13.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}
