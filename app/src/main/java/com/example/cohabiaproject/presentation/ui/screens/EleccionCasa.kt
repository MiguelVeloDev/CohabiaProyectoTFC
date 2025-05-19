package com.example.cohabiaproject.presentation.ui.screens



import android.os.Bundle
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import com.example.cohabiaproject.ui.theme.AnimacionCasa
import com.example.cohabiaproject.ui.theme.AnimacionLogin
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel



@Composable
fun EleccionCasa(modifier: Modifier = Modifier, navController: NavController) {






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

            Column (
                modifier = Modifier.fillMaxWidth().padding(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate(Screen.CrearCasa.route) },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        contentColor = NaranjaPrincipal,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xFFC0C0C0),
                        MaterialTheme.shapes.medium
                    )
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(horizontal = 50.dp, vertical = 15.dp),

                ) {
                    Text(text = "Crear una casa")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate(Screen.UnirmeCasa.route) },
                    colors = ButtonColors(
                        containerColor = Color(0xFFEE7A16),
                        disabledContainerColor = Color.Gray,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xFFC0C0C0),
                        MaterialTheme.shapes.medium
                    )
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(horizontal = 50.dp, vertical = 15.dp),
                ) {
                    Text(text = "Unirme a una casa")
                }
            }
            Spacer(modifier = Modifier.height(86.dp))

        }
    }
}