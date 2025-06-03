package com.example.cohabiaproject.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.twotone.Message
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Mensaje
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.MensajeViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel


@Composable
fun ChatScreen(navController: NavController) {
    val mensajeViewModel: MensajeViewModel = koinViewModel()
    var mensajeEnviar by remember { mutableStateOf("") }
    val mensajes = mensajeViewModel.mensajes.collectAsState(initial = emptyList())
    val usuariosViewModel: UsuarioViewModel = koinViewModel()
    val usuarios = usuariosViewModel.usuarios.collectAsState(initial = emptyList())


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Chat",
                navController = navController
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                if (mensajes.value.isEmpty()) {
                    ListaVaciaPlaceholder(
                        icono = Icons.TwoTone.Message,
                        texto = "mensajes"
                    )
                } else {
                    LazyColumn(
                        reverseLayout = true,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(mensajes.value) { mensaje ->
                            MensajeItem(mensaje = mensaje, usuarios.value)
                        }
                    }
                }
            }

                Row(
                    verticalAlignment = CenterVertically
                ) {
                    TextField(
                        value = mensajeEnviar,
                        onValueChange = { mensajeEnviar = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp).weight(1f),
                        placeholder = { Text("Escribe un mensaje...") },
                        singleLine = false,
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = FondoTextField,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = FondoTextField
                        )
                    )


                    IconButton(
                        onClick = {
                            mensajeViewModel.save(
                                Mensaje(
                                    usuario = Sesion.userId,
                                    contenido = mensajeEnviar
                                )
                            )
                            mensajeEnviar = ""
                            Log.d("ChatScreen", "Mensaje enviado: ${mensajes.toString()}")
                        },
                        enabled = mensajeEnviar.isNotBlank()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Enviar")
                    }
                }
            }
        }
    }


@Composable
fun MensajeItem(mensaje: Mensaje, usuarios: List<Usuario>) {
    if (mensaje.usuario == Sesion.userId) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Card(
                colors = CardDefaults.cardColors(containerColor = NaranjaPrincipal),
                modifier = Modifier.padding(4.dp),

            ) {
                Text(
                    text = mensaje.contenido,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                    color = Color.White
                )
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
                modifier = Modifier.padding(4.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)) {
                    Text(text = usuarios.find { it.id == mensaje.usuario }?.nombre ?: "Desconocido", fontWeight = FontWeight.Bold)
                    Text(text = mensaje.contenido)
                }
            }
        }
    }
}

