package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos




import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.cohabiaproject.presentation.ui.components.BottomNavBar

import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar



@Composable
fun MiPerfil(
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ){
                item {
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable(
                                onClick = { navController.navigate("misDatos") }
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                    )) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp).fillMaxWidth()
                        ) {
                            Text(
                                text = "Mis datos",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = "Icono de editar",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(15.dp)
                            )
                        }
                    }
                }
                item{
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable(
                                onClick = { navController.navigate("codigoCasa") }
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ))
                    {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp).fillMaxWidth()


                        ) {
                            Text(
                                text = "Invitar a unirse a mi casa",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = "Icono de editar",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(15.dp)
                            )
                        }
                    }
                }
                }
        }
    }
}

