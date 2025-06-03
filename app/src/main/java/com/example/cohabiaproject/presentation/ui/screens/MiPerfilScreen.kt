package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos




import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.twotone.PersonPin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.R
import com.example.cohabiaproject.domain.model.Sesion

import com.example.cohabiaproject.presentation.ui.components.BottomNavBar

import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun MiPerfil(
    navController: NavController,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val usuarioViewModel: UsuarioViewModel = koinViewModel()
    val usuario by usuarioViewModel.usuarioRegistrado.collectAsState()
    var correo = usuario?.correo ?: ""

    LaunchedEffect(Unit) {
        usuarioViewModel.obtenerUsuarioRegistrado()
    }



    Scaffold(
        containerColor = Color.White,
        topBar = { TopAppBarConFlecha(
            titulo = "Mi Perfil",
            navController = navController
        ) },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    onClick = {

                    },
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.PersonPin,
                        contentDescription = "Compartir código",
                        tint = NaranjaPrincipal,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = Sesion.nombreUsuario,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = correo,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable(
                                    onClick = { navController.navigate("codigoCasa") }
                                ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ))
                        {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp).fillMaxWidth()


                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir código",
                                    tint = NaranjaPrincipal,
                                )
                                Text(
                                    text = "Invitar a unirse a mi casa",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowForwardIos,
                                    contentDescription = "Icono de editar",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(15.dp)
                                )
                            }
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable(
                                    onClick = { navController.navigate("codigoCasa") }
                                ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ))
                        {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp).fillMaxWidth()


                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Compartir código",
                                )
                                Text(
                                    text = "Seguridad",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowForwardIos,
                                    contentDescription = "Icono de editar",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(15.dp)
                                )
                            }
                        }
                    }
                }


                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("login")

                            CoroutineScope(Dispatchers.IO).launch { Sesion.cerrarSesion() }
                        },
                    color = Color.Red
                )
            }
        }
    }
}

