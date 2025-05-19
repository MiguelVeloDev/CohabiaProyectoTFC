package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.cohabiaproject.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar

import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar



@Composable
fun CodigoCasa(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""





    Scaffold(
        containerColor = Color(0xFFF0F0F0),
        topBar = { MyTopAppBar(navController) },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {


            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.compartir_casa),
                    contentDescription = "Imagen de ejemplo"
                )
                TextField(
                    value = Sesion.casaId,
                    onValueChange = { Sesion.casaId = it },
                    label = { "Código de casa" },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

