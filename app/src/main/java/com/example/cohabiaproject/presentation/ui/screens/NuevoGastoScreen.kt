
package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.FondoTextField
import org.koin.androidx.compose.koinViewModel


@Composable
fun NuevoGastoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    usuarioPrimerPago: String
) {

    val finanzasViewmodel: FinanzasViewModel =
        koinViewModel()
    val listaFinanzas by finanzasViewmodel.finanzas.collectAsState(emptyList())
    var concepto by remember { mutableStateOf("") }


    var cantidad by rememberSaveable { mutableStateOf("") }
    val botonActivo = concepto.isNotBlank() && cantidad.isNotBlank()
    val usuarioViewModel: UsuarioViewModel = koinViewModel()
    val eventoViewModel : EventoViewModel = koinViewModel()
    var listaUsuarios = usuarioViewModel.usuarios.collectAsState(emptyList())
    var listaNombresUsuarios = listaUsuarios.value.map { it.nombre }
    val usuariosParticipan = remember { mutableStateListOf<String>((usuarioPrimerPago)) }
    var usuariosDeuda = remember { mutableStateListOf<String>() }
    var deuda by remember { mutableStateOf(false) }
    val usuarioPaga = usuariosParticipan.filter { it !in usuariosDeuda }


    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Nuevo gasto",
                textoBoton = "Guardar",
                navController = navController,
                accion = {finanzasViewmodel.save (finanzasViewmodel.crearFinanza(concepto = concepto, cantidad = cantidad.toDouble(), usuarioPaga = finanzasViewmodel.convertirNombreAId((usuarioPaga),listaUsuarios.value), usuariosParticipan = finanzasViewmodel.convertirNombreAId(usuariosParticipan, listaUsuarios.value),usuariosDeuda = finanzasViewmodel.convertirNombreAId(usuariosDeuda, listaUsuarios.value))); navController.navigate(
                    Screen.FinanzasScreen.route);eventoViewModel.save(Evento(tipo = "GASTO", contenido = eventoViewModel.generarMensaje("GASTO", cantidad))) },
                enabled = botonActivo
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = concepto,
                onValueChange = { concepto = it},
                label = { Text("Concepto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = "Usuario paga: $usuarioPaga",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = cantidad.toString(),
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )


            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Usuarios que participan:",
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                color = AzulGastos
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(listaNombresUsuarios){usuario ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth().padding(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

                        ) {
                            var marcado by remember { mutableStateOf(false) }
                            if (usuarioPaga.contains(usuario)) {marcado = true}
                            Text(usuario)
                            IconButton(
                                onClick = { marcado = !marcado; if (marcado) {
                                    usuariosParticipan.add(usuario.toString())
                                } else {
                                    usuariosParticipan.remove(usuario)
                                }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (!marcado) {
                                        Icons.Default.CheckBoxOutlineBlank
                                    } else {
                                        Icons.Default.CheckBox
                                    },
                                    contentDescription = "Opciones",
                                    tint = Color.Blue
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = deuda,
                    onCheckedChange = { deuda = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("¿Añadir deuda?")
            }

            if (deuda) {
                Text(
                    text = "Selecciona quién tiene deuda:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listaNombresUsuarios.filter { it != usuarioPrimerPago }) { usuario ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(2.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                var marcadoDeuda by remember { mutableStateOf(false) }
                                Text(usuario)
                                IconButton(
                                    onClick = {
                                        marcadoDeuda = !marcadoDeuda
                                        if (marcadoDeuda) {
                                            usuariosDeuda.add(usuario)
                                        } else {
                                            usuariosDeuda.remove(usuario)
                                        }
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (!marcadoDeuda) {
                                            Icons.Default.CheckBoxOutlineBlank
                                        } else {
                                            Icons.Default.CheckBox
                                        },
                                        contentDescription = "Seleccionar deuda",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

