package com.example.cohabiaproject.presentation.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TarjetaElectrodomestico
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    electrodomesticoViewModel: ElectrodomesticoViewModel = koinViewModel()

) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val casaViewModel: CasaViewModel = koinViewModel()
    val electrodomesticosEnEjecucion by electrodomesticoViewModel.electrodomesticosEnEjecucion.collectAsState(
        emptyList()
    )
    val casa by casaViewModel.casa.collectAsState(null)
    val nombreCasa = casa?.nombre ?: ""




    Scaffold(
        containerColor = Color(0xFFF0F0F0),
        topBar = { MyTopAppBar(navController) },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 16.dp)
                .padding(horizontal = 5.dp)
                .fillMaxSize()
                .background(White)
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
                        TarjetaElectrodomestico(electrodomestico = electrodomestico)
                    }

                }

            }
        }
    }
}

