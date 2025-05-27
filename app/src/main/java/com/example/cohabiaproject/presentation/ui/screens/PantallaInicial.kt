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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun PantallaInicial(navController: NavController) {
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        Sesion.cargarSesion()
        cargando = false
        Log.d("SesionPantallaInicial", Sesion.userId ?: "user null"+ Sesion.casaId?: "casa null")
    }

    if (cargando) {
        // Puedes mostrar un indicador de carga si quieres
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val user = Sesion.userId
        val casaId = Sesion.casaId
        val emailVerificado = Firebase.auth.currentUser?.isEmailVerified ?: false

        when {
            !emailVerificado -> {
                LaunchedEffect(Unit) {
                    navController.navigate("verificacionCorreo") {
                        popUpTo("launcher") { inclusive = true }
                    }
                }
            }

            user != null && casaId.isNullOrEmpty() -> {
                LaunchedEffect(Unit) {
                    navController.navigate("eleccionCasa") {
                        popUpTo("launcher") { inclusive = true }
                    }
                }
            }

            user != null -> {
                LaunchedEffect(Unit) {
                    navController.navigate("main") {
                        popUpTo("launcher") { inclusive = true }
                    }
                }
            }

            else -> {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("launcher") { inclusive = true }
                    }
                }
            }
        }

    }
}
