package com.example.cohabiaproject.presentation.ui.screens


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.viewmodel.LoginViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AnimacionLogin
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel


class RegisterScreen : ComponentActivity() {
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
fun Registro(modifier: Modifier = Modifier, navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaRepite by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }

    val loginViewModel: LoginViewModel = koinViewModel()
    val usuarioViewModel: UsuarioViewModel = koinViewModel()




    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center

            ){
                AnimacionLogin()
            }
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
                    unfocusedContainerColor = Color.White
                )
            )

            TextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White
                )
            )

            TextField(
                value = contrasenaRepite,
                onValueChange = { contrasenaRepite = it },
                label = { Text("Repite contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(50.dp))


            TextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(120.dp))


            Button(
                enabled = (contrasena == contrasenaRepite) && !correo.isEmpty() && !nombreUsuario.isEmpty() ,
                onClick = {
                    loginViewModel.registrar(correo, contrasena) { success, error ->
                        if (success) {
                            Sesion.userId = FirebaseAuth.getInstance().currentUser!!.uid
                            val usuario = Usuario(
                                id = Sesion.userId,
                                nombre = nombreUsuario,
                                correo = correo,
                                fotoPerfil = ""
                            )
                            usuarioViewModel.save(usuario)
                            Log.d("Registro", usuario.toString())
                            navController.navigate(Screen.EleccionCasa.route)

                        } else {
                            Log.e("Registro", "Error al registrar: $error")
                        }
                    };




                },
                colors = ButtonColors(containerColor = Color(0xFFEE7A16), disabledContainerColor = Color.Gray, contentColor = Color.White, disabledContentColor = Color.White),
                modifier = Modifier.border(1.dp, Color(0xFFC0C0C0), MaterialTheme.shapes.medium),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(horizontal = 50.dp, vertical = 15.dp),
            ) {
                Text(text = "Continuar")
            }
            Spacer(modifier = Modifier.height(86.dp))
            Text(
                text="Leer Terminos y Condiciones",
                style = TextStyle(fontSize = 14.sp, textDecoration = TextDecoration.Underline)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    CohabiaProjectTheme {

        Registro(modifier = Modifier, navController = rememberNavController())
    }
}
