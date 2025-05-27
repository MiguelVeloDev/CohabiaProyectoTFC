package com.example.cohabiaproject.presentation.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TarjetaElectrodomestico
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import com.example.cohabiaproject.ui.theme.LoadingAnimation
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val casaViewModel: CasaViewModel = koinViewModel()

    val casa by casaViewModel.casa.collectAsState(null)
    val nombreCasa = casa?.nombre ?: ""

    LaunchedEffect(Unit) {
        Sesion.cargarSesion()
    }
    if (Sesion.casaId == "") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation()
        }
    } else {
        val electrodomesticoViewModel: ElectrodomesticoViewModel = koinViewModel()

        val electrodomesticosEnEjecucion by electrodomesticoViewModel.electrodomesticosEnEjecucion.collectAsState()



        // 👇 Pantalla principal con datos
        Scaffold(
            containerColor = Color.White,
            topBar = { MyTopAppBar(navController,"") },
            bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 5.dp)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column {
                    Text(
                        text = nombreCasa,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    LazyColumn(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(electrodomesticosEnEjecucion) { electrodomestico ->
                            TarjetaElectrodomestico(
                                electrodomestico = electrodomestico,
                                navController
                            )
                        }
                    }
                    Button(
                        onClick = { navController.navigate("compras") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) { }
                }
            }
        }
    }
}