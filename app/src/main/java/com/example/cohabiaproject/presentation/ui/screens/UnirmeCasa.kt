package com.example.cohabiaproject.presentation.ui.screens


import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.AnimacionCasa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel



@Composable
fun UnirmeCasa(modifier: Modifier = Modifier, navController: NavController) {
    var codigo by remember { mutableStateOf("") }
    var casaViewModel: CasaViewModel = koinViewModel()
    var usuarioViewModel: UsuarioViewModel = koinViewModel()





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
                AnimacionCasa()
            }
            Spacer(modifier = Modifier.padding(15.dp))
            TextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Codigo de invitación") },
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
                enabled = (!codigo.isEmpty()),
                onClick = {

                    CoroutineScope(Dispatchers.Main).launch {
                        casaViewModel.unirmeCasa(codigo)
                        Sesion.cargarSesion()
                        navController.navigate(Screen.Main.route)
                        val usuario = usuarioViewModel.getById(Sesion.userId)
                        Log.d("Usuariongo" +
                                "", "${usuario.toString()}")
                        if (usuario != null) {
                            val usuarioActualizado = usuario.copy(casa = codigo)
                            usuarioViewModel.update(usuarioActualizado)

                            Sesion.cargarSesion()

                            navController.navigate(Screen.Main.route)
                        } else {
                            Log.e("CrearCasa", "Usuario no encontrado para actualizar casaId")
                        }

                    }



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
