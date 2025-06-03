package com.example.cohabiaproject.presentation.ui.screens.Compras
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.ShoppingCart
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

import org.koin.androidx.compose.koinViewModel


import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.DialogConfirmacion
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.ProductoViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.RojoCompras


@Composable
fun Compras(
    modifier: Modifier = Modifier,
    navController: NavController,
    productoViewModel: ProductoViewModel = koinViewModel()
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val listaProductos by productoViewModel.productos.collectAsState(initial = emptyList())

    val eventoViewModel : EventoViewModel = koinViewModel()
    val productosPorCategoria = listaProductos.groupBy { it.categoria }
    var  showDialog by remember { mutableStateOf(false) }
    var  showDialogGastos by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(
                titulo = "Compras",
                textoBoton = "Añadir",
                navController = navController,
                accion = { navController.navigate(Screen.AnadirProducto.route) },
                enabled = true
            )
        },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        if(listaProductos.isEmpty()){
            ListaVaciaPlaceholder(
                icono = Icons.Default.ShoppingCart,
                texto = "productos"
            )
            return@Scaffold
        }
        if (showDialog) {
            DialogConfirmacion(
                texto = "¿Terminar compra y borrar los productos comprados?",
                onDismiss = { showDialog = false },
                onConfirm = {
                    productoViewModel.borrarComprados(listaProductos.filter { it.comprado })
                    showDialog = false
                    eventoViewModel.save(Evento(
                        tipo = "COMPRA", contenido = eventoViewModel.generarMensaje("COMPRA", listaProductos.filter { it.comprado }.size.toString())))
                    showDialogGastos = true
                })}

        if (showDialogGastos) {
            DialogConfirmacion(
                texto = "¿Registrar el gasto de esta compra?",
                onDismiss = { showDialogGastos = false },
                onConfirm = {
                    showDialog = false
                    navController.navigate(Screen.SeleccionUsuarioGasto.route)
                })}
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {


            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp).weight(1f),

                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                productosPorCategoria.forEach { (categoria, productos) ->
                    if (productos.any { it.categoria == categoria && it.enLista }) {
                        item {
                            Text(
                                text = categoria,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = RojoCompras
                            )
                        }
                    }
                    items(productos) { producto ->
                        if (producto.enLista) {
                            ProductoItem(producto = producto, productoViewModel = productoViewModel)
                        }
                    }
                }

            }
            Box(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                contentAlignment = Alignment.Center


            ) {
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = RojoCompras),
                    enabled = listaProductos.any { it.comprado },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(text = "Finalizar compra")
                }
            }
        }
    }
}
@Composable
fun ProductoItem(producto: Producto, productoViewModel: ProductoViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = producto.nombre,
                    style = if (producto.comprado) {
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle()
                    }
                )
                if (producto.comentario.isNotBlank()) {
                Text(
                    text = producto.comentario,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    productoViewModel.update(producto.copy(comprado = !producto.comprado))
                }) {
                    Icon(
                        imageVector = if (producto.comprado) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription = "Marcar producto",
                        tint = NaranjaPrincipal
                    )
                }


            }

        }
        AnimatedVisibility(visible = expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3F3F3))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        value = producto.comentario,
                        onValueChange = { productoViewModel.update(producto.copy(comentario = it)) },
                        label = { Text("Comentario") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = FondoTextField,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent)
                    )
                    IconButton(onClick = { productoViewModel.borrar(producto.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar producto",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
