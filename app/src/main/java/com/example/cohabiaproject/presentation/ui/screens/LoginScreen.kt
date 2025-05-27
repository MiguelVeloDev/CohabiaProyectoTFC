package com.example.cohabiaproject.presentation.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.R
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.viewmodel.LoginViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import org.koin.androidx.compose.koinViewModel


class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CohabiaProjectTheme {

                Login(modifier = Modifier, navController = rememberNavController())
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier = Modifier, navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginViewModel: LoginViewModel = koinViewModel()
    var esError by remember { mutableStateOf(false) }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagen de ejemplo"
            )
            Spacer(modifier = Modifier.padding(15.dp))
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                    )
                )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = if (esError) "Correo o contraseña incorrectos" else "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            val enabled by remember(correo, password) {
                derivedStateOf {
                    correo.isNotEmpty() &&
                            password.isNotEmpty() &&
                            correo.contains("@") &&
                            correo.contains(".") &&
                            password.length >= 6
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 100.dp)
            ) {
                Button(
                    onClick = {
                        esError = false
                        loginViewModel.login(correo, password) { success, error ->
                            if (success) {
                                navController.navigate(Screen.Main.route)
                            } else {
                                Log.e("Login", "Error al iniciar sesión: $error")
                                esError = true
                            }

                        }
                    },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.White,
                        contentColor = Color(0xFFEE7A16),
                        disabledContentColor = Color(0xFFEE7A16)
                    ),
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xFFC0C0C0),
                        MaterialTheme.shapes.medium
                    ).fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(vertical = 15.dp),
                    enabled = enabled
                ) {
                    Text(text = "Iniciar sesión")
                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = { navController.navigate(Screen.Registro.route) },
                    colors = ButtonColors(
                        containerColor = Color(0xFFEE7A16),
                        disabledContainerColor = Color.White,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xFFC0C0C0),
                        MaterialTheme.shapes.medium
                    ).fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(vertical = 15.dp)
                ) {
                    Text(text = "Registrarse")
                }
            }
            Spacer(modifier = Modifier.height(86.dp))
            Text(
                text="Leer Terminos y Condiciones",
                style = TextStyle(fontSize = 14.sp, textDecoration = TextDecoration.Underline)
                ,
                modifier = Modifier.clickable(
                    onClick = {Log.d("Registro", Sesion.userId) }
                )

            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CohabiaProjectTheme {

        Login(modifier = Modifier, navController = rememberNavController())
    }
}
