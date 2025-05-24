package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.R
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VerificacionCorreo(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val scope = rememberCoroutineScope()
    var mensaje by remember { mutableStateOf("") }

    // 👇 Comprobación periódica
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            user?.reload()
            if (user?.isEmailVerified == true) {
                navController.navigate("eleccionCasa") {
                    popUpTo("verificacionCorreo") { inclusive = true }
                }
                break
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.correo),
                contentDescription = "Correo",
                modifier = Modifier
                    .height(180.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "📧 Verifica tu correo electrónico",
                style = MaterialTheme.typography.headlineSmall,
                color = NaranjaPrincipal
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Te hemos enviado un email de verificación. Revisa tu bandeja de entrada y haz clic en el enlace.",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            user?.sendEmailVerification()
                            mensaje = "Correo de verificación enviado ✉️"
                        } catch (e: Exception) {
                            mensaje = "Error al enviar el correo: ${e.message}"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NaranjaPrincipal)
            ) {
                Text("Reenviar correo")
            }

            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = mensaje, color = Color.Gray)
            }
        }
    }
}
