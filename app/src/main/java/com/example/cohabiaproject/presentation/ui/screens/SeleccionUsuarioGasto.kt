package com.example.cohabiaproject.presentation.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import org.koin.androidx.compose.koinViewModel


@Composable
fun SeleccionUsuarioGasto(
    modifier: Modifier = Modifier,
    navController: NavController,

) {

    val usuarioViewModel: UsuarioViewModel = koinViewModel()
    var listaUsuarios = usuarioViewModel.usuarios.collectAsState(emptyList()).value.map { it.nombre }
    var usuarioPaga by remember { mutableStateOf<String?>(null) }
    var botonActivo = usuarioPaga != null


    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Seleccionar usuario",
                textoBoton = "Siguiente",
                navController = navController,
                accion = { navController.navigate("nuevoGasto/${usuarioPaga}") },
                enabled = botonActivo)
        },


        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(listaUsuarios){usuario ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
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
                            Text(usuario)
                            IconButton(
                                onClick = {usuarioPaga = if (usuarioPaga != usuario) usuario else null

                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (usuario != usuarioPaga) {
                                        Icons.Default.CheckBoxOutlineBlank
                                    } else {
                                        Icons.Default.CheckBox
                                    },
                                    contentDescription = "Opciones",
                                    tint = AzulGastos
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

