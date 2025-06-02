package com.example.cohabiaproject.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.ui.theme.AnimacionCasa
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun PantallaInicial(navController: NavController) {
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        Sesion.cargarSesion()
        cargando = false
        Log.d("SesionPantallaInicial", (Sesion.userId ?: "user null") + (Sesion.casaId ?: " casa null"))
    }

    if (cargando) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val user = Sesion.userId
        val casaId = Sesion.casaId
        val emailVerificado = Firebase.auth.currentUser?.isEmailVerified ?: false

        val destino = when {
            user.isNullOrEmpty() -> "login"
            !emailVerificado -> "verificacionCorreo"
            !user.isNullOrEmpty() && casaId.isNullOrEmpty() -> "eleccionCasa"
            else -> "main"
        }

        LaunchedEffect(destino) {
            navController.navigate(destino) {
                popUpTo("launcher") { inclusive = true }
            }
        }
    }
}