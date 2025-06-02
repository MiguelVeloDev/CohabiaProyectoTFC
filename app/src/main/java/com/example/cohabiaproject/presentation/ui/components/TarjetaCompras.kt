package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.material.icons.filled.ShoppingCart
import com.example.cohabiaproject.ui.theme.RojoCompras


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cohabiaproject.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.ui.theme.AzulTareas
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
@Composable
fun TarjetaCompras(
    modifier: Modifier = Modifier,
    hayTareasHoy: Boolean,
    numeroTareas: Int,
    navController: NavController
) {
    Card(
        modifier = modifier
            .padding(2.dp).clickable { navController.navigate("compras") },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.TwoTone.ShoppingCart,
                    contentDescription = "Icono tareas",
                    tint =  Color(0xFF5E5E5E),
                    modifier = Modifier.size(48.dp).weight(2f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)


                    ) {


                    Text(
                        text = "Compras",
                        fontSize = 14.sp,
                        color =Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Icono tareas",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

    }
}
