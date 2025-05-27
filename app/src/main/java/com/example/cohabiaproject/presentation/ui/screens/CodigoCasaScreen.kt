package com.example.cohabiaproject.presentation.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.cohabiaproject.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar

import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal

@Composable
fun CodigoCasa(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Código de Casa",
                navController = navController
            )
        },

    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.compartir_casa),
                    contentDescription = "Imagen de ejemplo",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))


                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Este código identifica tu casa dentro de la aplicación. "
                            + "Puedes copiarlo para compartirlo con tus compañeros de vivienda "
                            + "o enviarlo para que puedan unirse a tu grupo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = Sesion.casaId,
                    onValueChange = { Sesion.casaId = it },
                    label = { Text("Código de casa") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        unfocusedContainerColor = FondoTextField,
                        focusedIndicatorColor = AzulGastos,
                        unfocusedIndicatorColor = Color.White,
                        cursorColor = AzulGastos
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    IconButton(
                        onClick = {
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CopyAll,
                            contentDescription = "Copiar código",
                            tint = AzulGastos,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        val contexto = LocalContext.current

                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "Únete a mi casa en Cohabia con este código: ${Sesion.casaId}")
                                }
                                contexto.startActivity(Intent.createChooser(intent, "Compartir con..."))
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir código",
                                tint = AzulGastos,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}
