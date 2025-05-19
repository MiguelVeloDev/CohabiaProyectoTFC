package com.example.cohabiaproject.presentation.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.cohabiaproject.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel


@Composable
fun MisDatos(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val usuarioViewModel: UsuarioViewModel = koinViewModel()
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fotoPerfil by remember { mutableStateOf("") }



    LaunchedEffect(Dispatchers.Main) {
        val usuario = usuarioViewModel.getById(Sesion.userId)
        nombre = usuario?.nombre ?: ""
        correo = usuario?.correo ?: ""
        fotoPerfil = usuario?.fotoPerfil ?: ""
    }


        Scaffold(
            containerColor = Color(0xFFF0F0F0),
            topBar = { MyTopAppBar(navController) },
            bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {


                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Imagen de ejemplo",
                        modifier = Modifier
                            .clip(CircleShape)
                    )
                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { "Código de casa" },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    TextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { "Correo" },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

