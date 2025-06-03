package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.cohabiaproject.ui.theme.AnimacionCasa



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.presentation.ui.components.DialogConfirmacion
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal

@Composable
fun CasaScreen(navController: NavController) {
    val usuarioViewModel: UsuarioViewModel = koinViewModel()
    val usuarios by usuarioViewModel.usuarios.collectAsState()
    val casaViewModel: CasaViewModel = koinViewModel()
    val casa by casaViewModel.casa.collectAsState()



    Scaffold(
        containerColor = Color.White,
        topBar = {
                TopAppBarConFlecha(
                    titulo = "Mi Casa",
                    navController = navController
                )
            }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center ){
                    AnimacionCasa()
                }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = casa?.nombre ?: "",
                fontSize = 24.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Usuarios",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NaranjaPrincipal
            )
            Spacer(modifier = Modifier.height(26.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usuarios) { usuario ->
                    UsuarioCard(usuario = usuario, onBorrar = {
                        usuarioViewModel.borrar(usuario)
                    })
                }
            }
        }
    }
}
@Composable
fun UsuarioCard(usuario: Usuario, onBorrar: () -> Unit) {
    var confirmarEliminar by remember { mutableStateOf(false) }

    if (confirmarEliminar) {
        DialogConfirmacion(
            texto = "Eliminar ${usuario.nombre} de la casa",
            onDismiss = { confirmarEliminar = false },
            onConfirm = {
                onBorrar
                confirmarEliminar = false
            },
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.PersonOutline,
                contentDescription = "Usuario",
                tint = NaranjaPrincipal,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = usuario.nombre,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {confirmarEliminar = true}) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Gray
                )
            }
        }
    }
}
