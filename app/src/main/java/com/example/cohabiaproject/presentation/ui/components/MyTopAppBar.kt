package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.R
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController, titulo : String) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Text(text = titulo) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        ),
        actions = {
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(
                    onClick = {navController.navigate("chat")},
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Mensajes"
                    )
                }

            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { expanded = true }
            ) {
              IconButton(
                  onClick = {
                      navController.navigate("miPerfil")},
                  colors = IconButtonDefaults.iconButtonColors(
                      containerColor = Color(0xFFF3EAE8)
                  )
              ) {
                  Icon(
                      imageVector = Icons.Default.PersonOutline,
                      contentDescription = "Mi perfil",
                      tint = NaranjaPrincipal
                  )
              }


            }
        }
    )
}
