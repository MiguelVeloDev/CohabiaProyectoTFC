package com.example.cohabiaproject.presentation.ui.screens


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TarjetaElectrodomestico
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal


class ListaElectrodomesticosScreen : ComponentActivity() {
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
fun ListaElectrodomesticosScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    electrodomesticoViewModel: ElectrodomesticoViewModel = koinViewModel()
) {
    val electrodomesticosLista by electrodomesticoViewModel.electrodomesticos.collectAsState(emptyList())
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Electrodomésticos",
                navController = navController
            )
        },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Card(
            modifier = Modifier
                .fillMaxWidth() ,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Nuevo electrodoméstico",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Icono más para nuevo electrodoméstico",
                    tint = MoradoElectrodomesticos,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(onClick = { navController.navigate("nuevoElectrodomestico") })
                )
            }
        }
            Text(text = "Mis Electrodomésticos", modifier = Modifier.padding(bottom = 8.dp).padding(top = 16.dp), fontWeight = FontWeight.Bold, fontSize = 24.sp)


            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 10.dp)
        ) {
            items(electrodomesticosLista) { electrodomestico ->
                TarjetaElectrodomestico(electrodomestico, navController)
            }
        }
    }
}

}